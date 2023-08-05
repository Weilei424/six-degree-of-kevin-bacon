*** Settings ***
Library    Collections
Library    RequestsLibrary
Test Timeout    30 seconds

Suite Setup    Create Session    localhost    http://localhost:8080/api/v1
*** Variables ***
${actorId}    actorId
${movieId}    movieId
${addActor}    /addActor
${addMovie}    /addMovie
${addRelationship}    /addRelationship
${getActor}    /getActor
${getMovie}    /getMovie
${hasRelationship}    /hasRelationship
${computeBaconNumber}    /computeBaconNumber
${computeBaconPath}    /computeBaconPath
${newFeature}
${KevinBacon}       nm1001231
${M01}    0001
${A01}    0001
${A15}    0015
${A20}    0020
*** Test Cases ***
# addActorPass
# addActorFail
# addMoviePass
# addMovieFail
# addRelationshipPass
# addRelationshipFail
# getActorPass
# getActorFail
getMoviePass
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${endpoint_with_param}=    Set Variable   ${getMovie}?${movieId}=${M01}
    ${resp}=    Get Request    localhost    ${endpoint_with_param}
    Should Be Equal As Strings    ${resp.status_code}    200
    ${json}=    To Json    ${resp.content}
    Should Be Equal As Strings    ${json['movieId']}    0001
    Should Be Equal As Strings    ${json['name']}    M01
# getMovieFail
# hasRelationshipPass
# hasRelationshipFail
# computeBaconNumberPass
# computeBaconNumberFail
# computeBaconPathPass
# computeBaconPathFail