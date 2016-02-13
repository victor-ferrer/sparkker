# Sparkker: A Spark Analysis module for [Stokker](https://github.com/victor-ferrer/stokker)

[![Travis CI Status](https://travis-ci.org/victor-ferrer/sparkker.svg?branch=master)](https://travis-ci.org/victor-ferrer/sparkker)

## Summary

This a personal learning project whose functionality, for the moment, is to:
- Using Spring Cloud Netflix orchestrate itself with the [Stokker](https://github.com/victor-ferrer/stokker) and [Portfolio-Manager](https://github.com/victor-ferrer/stokker-portfolio-manager) modules.
- Retrieve Stock quotations via Rest
- Perform analysis in a Spark Module (calculate deviations, moving averages, etc.)
- Send them back to Stokker to be stored

This is a snapshot of the current user interface showing a Stock being plotted along with some Indicators (Simple Moving Average (SMA), Max/Min value in a sliding period of time, etc.)

![UI Screenshot](https://raw.githubusercontent.com/victor-ferrer/sparkker/master/ui_screenshot.PNG)

## Technology stack
- Spring Boot 1.3.0 in the Server Side.
- Apache Spark 
- AngularJS in the front-end.
- Angular Chart to show stock charts.

## Status
This is work in progress:
- Updated libraries - Startup succesfull with Eureka but still can´t register.
- Stocks might be retrieved from Stokker or from a sample file.
- The result is just printed in the stdout and resulting web page. It should be sent to Stokker to be indexed in Elastic Search.
