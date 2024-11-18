name: Docker CI

on:
  workflow_dispatch:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:

  test_dockerized_webserver:

    runs-on: ubuntu-latest

    permissions:
      contents: read

    steps:

      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: maven

      - name: Setup Docker buildx
        uses: docker/setup-buildx-action@v3

      - name: Install Chrome Web Browser
        run: sudo apt-get -y install google-chrome-stable

      - name: Install Chrome Web Driver
        run: selenium/manager/linux/selenium-manager --browser chrome

      - name: Launch Web Service
        run: docker compose up -d

      - name: Run Selenium Tests
        run: cd selenium && mvn test
