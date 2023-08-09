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
${M1}    0001
${fakeMovieId}    9000
${fakeMovieName}    nOsucHmoviE
${fakeActorId}    9876
${fakeActorName}    Void
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
    ${endpoint_with_param}=    Set Variable   ${getMovie}?${movieId}=${M1}
    ${resp}=    GET On Session    localhost    ${endpoint_with_param}
    Should Be Equal As Strings    ${resp.status_code}    200
    ${json}=    To Json    ${resp.content}
    Should Be Equal As Strings    ${json['movieId']}    0001
    Should Be Equal As Strings    ${json['name']}    M1
getMovieFail
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${endpoint_with_param1}=    Set Variable   ${getMovie}?${movieId}=${fakeMovieId} 
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param1}  

    ${endpoint_with_param2}=    Set Variable   ${getMovie}?GETMOVIEEEEEE=${M1}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param2}    headers=${headers}
# hasRelationshipPass
# hasRelationshipFail
# computeBaconNumberPass
# computeBaconNumberFail
# computeBaconPathPass
# computeBaconPathFail