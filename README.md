# Starter Template

---

This repository contains the JAVA code for a working Java 11 starter project.  Key points are:

  - Java 11 (AWS Corretto).
  - The project is based on Maven.
  - Uses the smc-api-lib package.
  - AWS Labs Serverless-Containers are used to integrate with AWS API Gateway.
  - Routing is handled through Jersey.
  - Responses with Jackson.
  - Dagger for dependency injection.

## Pre-requisite

You will need to install the following tools to make sure you have the required security checks in place:

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

    # Application client ID from Azure
    azure_client_id: [populate as appropriate]

    # DVSA Azure Tenant ID
    azure_tenant_id: [populate as appropriate]

## Running The API Locally
- Create the config directory and `settings.yaml` file
- Run the JettyLocalRunner class (use the IntelliJ [Env plugin](https://plugins.jetbrains.com/plugin/7861-envfile) to point to the config.yaml file)
  - Update VM options as specified above
- Using [Postman](https://www.postman.com) hit the two example end-points
  - `localhost:8000/1.0/version`
  - `localhost:8000/1.0/helloworld`
- Standard debugging is possible anywhere within the code just add a breakpoint

## Maven Commands
Use the following Maven commands to run various project Lifecycle Goals:

| Goal            | Command                               | Description                                                   |
|-----------------|---------------------------------------|---------------------------------------------------------------|
| **Test**        | ```mvn clean test```                  | *Run the jUnit tests only*                                    |
| **Swagger**     | ```mvn clean compile```               | *Generates the ```swagger.json``` file*                       |
| **Sonar**       | ```mvn clean test sonar:sonar```      | *Generate coverage reports and sends to the SonarQube server* |
| **Build**       | ```mvn clean package```               | *Runs the tests and builds the zip*                           |

