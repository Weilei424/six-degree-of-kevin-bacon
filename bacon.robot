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
${KevinBaconId}       nm1001231
${KevinBacon}    Kevin Bacon
${A01}    0001
${A23}    0023
${M1}    0001
${M9}    0009
${M12}    0012
${fakeMovieId}    9000
${fakeMovieName}    nOsucHmoviE
${fakeActorId}    9876
${fakeActorName}    Void
*** Test Cases ***
# addActorPass
# addActorFail
# addMoviePass
# addMovieFail
addRelationshipPass
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=${A23}    movieId=${M12}
    ${params_json}=    Convert To String    ${params}
    ${resp}=    PUT On Session    localhost    ${addRelationship}     data=${params_json}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200
addRelationshipFail
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params1}=    Create Dictionary    actorId=${fakeActorId}    movieId=${M12}
    ${params_json1}=    Convert To String    ${params1}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${addRelationship}    data=${params_json1}    headers=${headers}

    ${params2}=    Create Dictionary    actorId=${A23}    movieId=${fakeMovieId}
    ${params_json2}=    Convert To String    ${params2}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${addRelationship}    data=${params_json2}    headers=${headers}

    ${params3}=    Create Dictionary    actorId=${A23}    movieId=${M12}
    ${params_json3}=    Convert To String    ${params3}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    /addRELATIONSHIP    data=${params_json3}    headers=${headers}

    # ${params4}=    Create Dictionary    actorIdd=${A23}    movieId=${M12}
    # ${params_json4}=    Convert To String    ${params4}
    # Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${addRelationship}    data=${params_json3}    headers=${headers}
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