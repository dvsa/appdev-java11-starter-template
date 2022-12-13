# Starter Template

---

This repository contains the JAVA code for a starter template.  Key points are:

  - The project is based on Maven.
  - AWS Labs Serverless-Containers are used to integrate with AWS API Gateway.
  - Routing is handled through Jersey.
  - Responses with Jackson.
&nbsp;

## Pre-requisite

You will need to install the following DVSA packages to make sure you have the security checks required in place:

- [git-secrets](https://github.com/awslabs/git-secrets)
- [repo-security-scanner](https://github.com/UKHomeOffice/repo-security-scanner)
- [snyk-cli](https://docs.snyk.io/snyk-cli/install-the-snyk-cli)

## Getting Started

Pull repository:
```shell
git clone git@github.com:dvsa/smc-java11-starter-template.git
```

## Java 11

Make sure to change your Java home
```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/amazon-corretto-11.jdk/Contents/Home
```

The following must be added to your VM options:
```
-ea -Duser.timezone=UTC --add-opens java.base/java.lang=ALL-UNNAMED
```

## Configuration
A settings.yaml file must be created under the /config folder in this
project. Populate that file with the following configuration:

    # Starter Template API Service
    # Configuration settings allowing Maven to deploy the application to the Development environment.
    # Use the EnvFile plugin from IntelliJ to use it locally.
    # --------------------------------------------------------------------------------------------------------------------
    
    # Environment definition so the JarFile knows where its being run. Certain security options are turned off in Dev
    environment: development
    
    # Secret Key to retrieve values from AWS Secrets Manager
    secretkey: [populate as appropriate]

## Maven Commands
Use the following Maven commands to run various project Lifecycle Goals:

| Goal            | Command                               | Description                                                   |
|-----------------|---------------------------------------|---------------------------------------------------------------|
| **Test**        | ```mvn clean test```                  | *Run the jUnit tests only*                                    |
| **Swagger**     | ```mvn clean compile```               | *Generates the ```swagger.json``` file*                       |
| **Sonar**       | ```mvn clean test sonar:sonar```      | *Generate coverage reports and sends to the SonarQube server* |
| **Build**       | ```mvn clean package```               | *Runs the tests and builds the zip*                           |

