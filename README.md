# eCFR Analyzer

A web application for analyzing Federal Regulations data from the Electronic Code of Federal Regulations (eCFR).

## Overview

This project provides analytics and visualizations of Federal Regulations by leveraging the public eCFR API available at https://www.ecfr.gov/. 

## Features

- Download and analyze current eCFR data
- Calculate metrics like:
  - Word count per agency
  - Historical changes over time
  - [Placeholder for custom analytics]
- Interactive frontend visualization dashboard
- Query and filter capabilities

## Run locally service

`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

### Clean, package, and then run service
`mvn clean package spring-boot:run -Dspring-boot.run.profiles=dev`

### If package doesn't install new maven dependency run the following:

`mvn clean install -U`
