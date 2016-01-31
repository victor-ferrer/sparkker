# Sparkker: A Spark Analysis module for Stokker

## Summary

This a personal learning project whose functionality, for the moment, is to:
- Using Spring Cloud Netflix orchestrate itself with the Stokker and Portfolio-Manager modules.
- Retrieve Stock quotations via Rest
- Perform analysis in a Spark Module (calculate deviations, moving averages, etc.)
- Send them back to Stokker to be stored

## Status
This is work in progress:
- I have class-loading problems so I can not use Eureka.
- There is a dummy controller that triggers the Spark process which reads a sample text file
- I need to replace that with a rest call to Stokker with the proper parameters
- The result is just printed in the stdout and resulting web page. It should be sent to Stokker to be indexed in Elastic Search.
