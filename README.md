# history-cascalog

Example to evaluate Cascalog

## Installation

Make sure Hadoop is setup.For development setup a single node Hadoop cluster.

## Usage

Copy the input files to HDFS with the command:
    hadoop fs -put resources/data/moz_historyvisits.csv data
    hadoop fs -put resources/data/moz_places_filtered.txt data

Remember to clear output directory with:
    hadoop fs -rmr output

Run the example with:
    lein uberjar
    hadoop jar ./target/history.jar

You can get the output by using the command:
    hadoop fs -get output output

