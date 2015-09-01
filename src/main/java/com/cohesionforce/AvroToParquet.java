/*******************************************************************************
 * Copyright (c) 2015 CohesionForce Inc
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CohesionForce Inc - initial API and implementation
 *******************************************************************************/
package com.cohesionforce;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.hadoop.fs.Path;

import parquet.avro.AvroParquetWriter;
import parquet.hadoop.ParquetWriter;
import parquet.hadoop.metadata.CompressionCodecName;

import org.apache.commons.io.FilenameUtils;

public class AvroToParquet {
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Usage: ?>java -jar <jarfile> Filename.avro");
			return;
		}

		File avroFile = new File(args[0]);

		if (!avroFile.exists()) {
			System.err.println("Could not open file: " + args[0]);
		}
		try {

			DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>();
			DataFileReader<GenericRecord> dataFileReader;
			dataFileReader = new DataFileReader<GenericRecord>(avroFile, datumReader);
			Schema avroSchema = dataFileReader.getSchema();

			// choose compression scheme
			CompressionCodecName compressionCodecName = CompressionCodecName.SNAPPY;

			// set Parquet file block size and page size values
			int blockSize = 256 * 1024 * 1024;
			int pageSize = 64 * 1024;

			String base = FilenameUtils.removeExtension(avroFile.getAbsolutePath());
			
			Path outputPath = new Path("file:///"+base+".parquet");

			// the ParquetWriter object that will consume Avro GenericRecords
			ParquetWriter<GenericRecord> parquetWriter;
			parquetWriter = new AvroParquetWriter<GenericRecord>(outputPath, avroSchema, compressionCodecName, blockSize, pageSize);
			for (GenericRecord record : dataFileReader) {
				parquetWriter.write(record);
			}
			dataFileReader.close();
			parquetWriter.close();
		} catch (IOException e) {
			System.err.println("Caught exception: " + e.getMessage());
		}
	}
}
