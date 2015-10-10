# AvroToParquet Converter

This repository contains a simple command line converter for Apache Avro to Apache Parquet file formats.  The converter reads the schema from the input Avro file and uses it to write out a corresponding file in Parquet format.  The converter is intended to be used with files on a local filesystem (not in Hadoop) and does not require any other framework such as Spark or Hive.

Parquet has an additional dependency that the schema must be a "record" type in Avro.

## Dependencies
This converter has the following dependencies:
* Apache Avro v1.7.7 - Used to read the Avro input files
* Apache Commons-IO v2.4 - Used to get the base filename and change the extention to 'parquet'
* Apache Commons-CLI v1.3.1 - Used to parse command line parameters
* Parquet v1.6.0rc3.  This version of Parquet was chosen to be compatible with Apache Spark v1.4.1, which also uses Parquet v1.6.0rc3.

## Building

?>git clone https://github.com/CohesionForce/avroToParquet.git

?>cd avroToParquet

?>mvn package

The result of the build is an executable jar located at target/avroToParquet-1.0.0-jar-with-dependencies.jar

## Running

?>java -jar avroToParquet-1.0.0-jar-with-dependencies.jar -i AvroFile.avro

where AvroFile.avro is a binary file in Apache Avro format.  The default output file is the same as the input file with the extension changed to 'parquet'.  The default can be changed by supplying the desired output file with an -o <filename> parameter.

## Testing

Example avro files can be found in the Apache Avro repository on Github: 
[https://github.com/apache/avro/tree/trunk/share/test/data](https://github.com/apache/avro/tree/trunk/share/test/data)
