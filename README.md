# Campaign Application
> In online advertising, a valuable technology is "targeting". Targeting allows advertisers to deliver advertising content
specific to certain user attributes such as geographic location, age or which websites the user has visited.

## Table of contents
* General info
* Specification
* Technologies
* Setup
* Features
* Algorithm


## General info
The program will take a single argument, a file containing a list of campaigns. This file will consist of several rows,
each with a variable number of columns. The first column is the unique identifier of a campaign (a single word). The
columns after the first are the segments that the campaign is targeting. Duplicate columns and/or rows are valid and to
be ignored.

## Specification
Once the program has loaded the data file, it will be ready to accept input from users. Lines of data will be read from
standard input where each line consists of a list of segments some user is associated with. It is not guaranteed that a
segment a user belongs to has any campaigns associated with it. Given this list of segments, a campaign containing the
largest set of common segments is to be selected. Once a campaign has been selected, the name of the campaign is to be
printed on a single line. If no eligible campaign is found, "no campaign" is to be printed on a single line.

## Technologies
* Java - version 13.0
* SpringBoot - version 2.4.3
* SpringBatch
* Gradle
* JUint5

## Setup
* Unzip the project
* Open the project in the ide 
* Open the Terminal
* Build the project using [./Gradlew build]

## Features
> I loaded the whole Campaign file in a Map which have keys as segments and values as a set of Campaigns in applicaton startup.
> Then I used SpringBatch to get the Test cases from the input file as chunks, then process each chunk according to the algorithm,then write output in a new file which located in campaignAssignment/output.

## The processing algorithm

* Finding Matched Campaigns from the loaded map based on the input segments from user
* Finding the set of Campaigns that have the maxmium segements occurences and have the same weight
* I created a distrbution to decrease the probability of starving a specific campaign by recording all the winning Campaings in map, then finding the most starved campagin.   
