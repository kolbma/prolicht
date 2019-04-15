# JEE Applikation zur Bewerbung bei PROLICHT

## Beschreibung

Eine auf SpringBoot basierende JEE-Webapplikation zur Generierung eines Online-Lebenslaufes.
Im Prinzip ein leichtgewichtiges, einfaches, sehr spezialisiertes ContentManagementSystem (CMS) mit Bearbeitung von in Datenbank hinterlegten Daten über REST Webservice (JSON+HAL).
Das Datenbank-Backend, das Webservice-Backend, der Webservice-Client und das Webapplikations-Frontend sind in einer Applikation zusammengefasst.
Die Webservice-API ist mit Hilfe von Swagger dokumentiert und über **/v2/api-docs** abrufbar.
Außerdem steht das Swagger-UserInterface unter **/swagger-ui.html** zur Verfügung.

Da dies nur eine Art **Proof-Of-Concept** oder Möglichkeitsstudie ist, sind weder Benutzeradministration noch Mehrmandantenfähigkeit komplett integriert.

Die Applikation veranschaulicht die Funktionsweise von Spring(Boot) bei REST-Webservices und JPA und Freemarker-Templates.

## Konfiguration

Zum Start der WebApplikation (in aktueller Fassung) müssen die Umgebungsvariablen **PROLICHTUSER** und **PROLICHTPASSWORD** gesetzt sein.
Mit diesem Benutzer/Passwort kann auf per HTTPBasicAuth abgesicherte APIs zugegriffen werden.


## Architektur

![](https://www.plantuml.com/plantuml/svg/VPDFRzim3CNl-XIy3qKm7tSOsWO6KWp3XYRTfSY1iHbRgq8QCbCl6FRTHoNkh7bhSv3otkzHalNdkbUCUZm4rpzvxZ3Y0IevTCB4kjPJ831OPBH5heVeLOb1uv6SywmKzrWHt4OPUegvTL-kDylWYHMmX_lb-pTOCIzqB4eGQAyW-xC37s3nN2omytbsTRCflwmlwUYRDiL7qmfGOG63rnQmwuAlKBtmudil_Ax5u5o7rHCsjdhhYUkbi4O9WM9QrMsvX9yExFTuKrLoP9tuTlGle_w7bCzaBYer4a-lqBcxajtkDA-PqRdxYMK8L3SqrRsQc9ElhQZaxY_d7bUe97tcmjNLnxGNnkwufXzpP0Sxac-UXYsOzK_GZTSZmIph9Z-1zO1DG_7XRkko_zFyDfC9K-awc2foPSrBSWgEjoD5Sttfpl7vvpfNe-8EUu9LyNBy6zm5wi_xOyDV5bkoUZrzbfg2aRzbIanL3YkL20_fUQHctBNYZvWUppLnRO_kDm00)

```plantuml
@startuml
skinparam componentStyle uml2
skinparam handwritten true

interface Browser

interface RESTClient as RC1
interface RESTClient as RC2

interface REST
note left of REST : /v2/api

interface HATEOS
note right of HATEOS : at / with application/json+hal

package BackendController as BEC {
    [AccountController]
    [ApplicantController]
    [EventCategoryController]
    [EventController]
    [HobbyCategoryController]
    [HobbyController]
    [KnowledgeCategoryController]
    [KnowledgeController]
    [PhotoController]
}

[HateosController] --> BEC

[IndexController] --> RC1
[IndexView] -> [IndexController]
Browser -> [IndexView]

Browser --> [SwaggerUI]
[SwaggerUI] --> BEC

RC1 ---> REST
RC2 ---> REST
REST --> BEC
RC1 ---> HATEOS
RC2 ---> HATEOS
HATEOS --> HateosController

database H2 {
    [Tables]
}

BEC --> [EntityModels]
[EntityModels] --> [Tables]

actor User
User --> RC2
User --> Browser

@enduml
```

## Datenmodell

![](https://www.plantuml.com/plantuml/svg/hLHDR-8m4BtxLtYv059RxLuHGaqXNL19WY2jnAaozW1M7JlhEALejVzzYO4i-G1MQdD9_6Ry_ERDEFU98TgaaK396vSnqIJ2QoBPHdDZG6AZKq3e6uCbbu0DMGXeVRGn5IH9y0VkTd4hl26x1w2DUps4zYaEfIgLfer_8xnxEElWaH_YxkIfPu4q0Ir911tyuapxGsTgqNX7kb6Q5L6gWHXWn7HmEFBSMUXuapr9pAg1Fxc4E1QSadeHkFjuK4BsIb_pw36mv3enLOs2rA69qG2cL8rA35Mib4cvsPRoragMaY0YN1IX1TTcpSXkxy09tNzGB0WjxMQQltEv4fo2J4gnovuzIRKHm5PmsREtOzeh9GPMIc-hzjLu1BzIa1HiOisP_GFNbNuTs_g98zrtk3OMaALSACzUcypAatw0P2KaMsf8b4WDLxA3NV_PQpNdhZDjtk2c5_ZXCFiOEFDcssumt8XoDo6XciUM80yKI_aIvuxgN6yo3kQUwuUPjCaqU7Hdir7WE-E1-v91xk2vxuGx95lC-iCW68_ydyts9SU7Qh6ebMU1THRztragaNvHxHNooLePLIMzpiU88HtyC0uUx382nacuZKjTNN81bPCFVqtS-D70jxVsbV_54Ce_y_ZtkplPmFa5EfzYf-Hys1fvFcojG2W_vrHVvVQY2d9UhfLrobQOPbHOrLTabPyo56O25LRrHT-3P6aa_W80)

```plantuml
@startuml
skinparam handwritten true

!define table(x) class x << (T,aaaaee) >>

table(Account) {
    id: INT <PK>
    username: VARCHAR
    password: VARCHAR
    createdat: TIMESTAMP
    updatedat: TIMESTAMP
}

table(Applicant) {
    id: INT <PK> <FK>
    account_id: INT <FK>
    firstname: VARCHAR
    lastname: VARCHAR
    street: VARCHAR
    postcode: VARCHAR
    city: VARCHAR
    phone: VARCHAR
    email: VARCHAR
    birthday: DATE
    birthplace: VARCHAR
    drivinglicense: VARCHAR
    updatedat: TIMESTAMP
}

table(Knowledge) {
    id: INT <PK> <FK>
    knowledge_category_id: INT <FK>
    name: VARCHAR
    sequence: INT
}

table(KnowledgeCategory) {
    id: INT <PK> <FK>
    applicant_id: INT <FK>
    name: VARCHAR
    sequence: INT
}

table(Event) {
    id: INT <PK> <FK>
    event_category_id: INT <FK>
    startdate: DATE
    enddate: DATE
    dateresolution: ENUM('YEAR', 'MONTH', 'DAY')
    title: VARCHAR
    description: VARCHAR
}

table(EventCategory) {
    id: INT <PK> <FK>
    applicant_id: INT <FK>
    name: ENUM('EMPLOYMENT', 'PROFESSIONALDEV', 'EDUCATION', 'SCHOOLING')
    sequence: INT
}

table(Hobby) {
    id: INT <PK>
    hobby_category_id: INT <FK>
    name: VARCHAR
}

table(HobbyCategory) {
    id: INT <PK> <FK>
    applicant_id: INT <FK>
    name: VARCHAR
}

table(Photo) {
    id: INT <PK>
    applicant_id: INT <FK>
    data: BLOB
    mediaType: VARCHAR
    filename: VARCHAR
}

Account "1" -- "1" Applicant

Applicant "1" -- "0..n" Knowledge

Applicant "1" -- "0..n" Event

Applicant "1" -- "0..n" Hobby

Applicant "1" -- "0..n" Photo

Knowledge "1" -- "1" KnowledgeCategory
KnowledgeCategory "1" -- "0..n" Knowledge

Event "1" -- "1" EventCategory
EventCategory "1" -- "0..n" Event

Hobby "1" -- "1" HobbyCategory
HobbyCategory "1" -- "0..n" Hobby

@enduml
```
