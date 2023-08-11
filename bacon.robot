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
${A30}    0030
${M1}    0001
${M9}    0009
${M12}    0012
${fakeMovieId}    9000
${fakeMovieName}    nOsucHmoviE
${fakeActorId}    9876
${fakeActorName}    Void
${newActor1id}    0080
${newAcotr1name}    Aaron Paul
${newActor2id}    0081
${newAcotr2name}    The Rock
${newActor3id}    0082
${newAcotr3name}    Jimmy Oyang
${newMovie1id}    9001
${newMovie1name}    New Movie 1
${newMovie2id}    9002
${newMovie2name}    New Movie 2    
*** Test Cases ***
# addActorPass
# addActorFail
# addMoviePass
# addMovieFail
addRelationshipPass
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=${A30}    movieId=${M12}
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

    ${params4}=    Create Dictionary    actorIdd=${A23}    movieId=${M12}
    ${params_json4}=    Convert To String    ${params4}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${addRelationship}    data=${params_json4}    headers=${headers}

    ${params5}=    Create Dictionary    actorIdd=${A23}    
    ${params_json5}=    Convert To String    ${params5}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${addRelationship}    data=${params_json5}    headers=${headers}
# getActorPass
# getActorFail
getMoviePass
    ${endpoint_with_param}=    Set Variable   ${getMovie}?${movieId}=${M1}
    ${resp}=    GET On Session    localhost    ${endpoint_with_param}
    Should Be Equal As Strings    ${resp.status_code}    200
    ${json}=    To Json    ${resp.content}
    Should Be Equal As Strings    ${json['movieId']}    0001
    Should Be Equal As Strings    ${json['name']}    M1
    Should Be True    isinstance(${json['actors']}, list)     
getMovieFail
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${endpoint_with_param1}=    Set Variable   ${getMovie}?${movieId}=${fakeMovieId} 
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param1}  

    ${endpoint_with_param2}=    Set Variable   ${getMovie}?GETMOVIEEEEEE=${M1}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param2}    headers=${headers}
hasRelationshipPass
    ${endpoint_with_param1}=    Set Variable   ${hasRelationship}?${actorId}=${A23}&${movieId}=${M12}
    ${resp1}=    GET On Session    localhost    ${endpoint_with_param1}
    Should Be Equal As Strings    ${resp1.status_code}    200
    ${json1}=    To Json    ${resp1.content}
    Should Be Equal As Strings    ${json1['movieId']}    0012
    Should Be Equal As Strings    ${json1['actorId']}    0023
    Should Be True    ${json1['hasRelationship']} 

    ${endpoint_with_param2}=    Set Variable   ${hasRelationship}?${actorId}=${A30}&${movieId}=${M1}
    ${resp2}=    GET On Session    localhost    ${endpoint_with_param2}
    Should Be Equal As Strings    ${resp2.status_code}    200
    ${json2}=    To Json    ${resp2.content}
    Should Be Equal As Strings    ${json2['movieId']}    0001
    Should Be Equal As Strings    ${json2['actorId']}    0030
    Should Be True    not ${json2['hasRelationship']} 
hasRelationshipFail
    ${endpoint_with_param1}=    Set Variable   ${hasRelationship}?${actorId}=${A23}&movieIddd=${M12}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param1} 

    ${endpoint_with_param2}=    Set Variable   ${hasRelationship}?${actorId}=${A23}&${movieId}=${fakeMovieId}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param2} 

    ${endpoint_with_param3}=    Set Variable   ${hasRelationship}?${actorId}=${fakeActorId}&${movieId}=${M12}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param3} 
# computeBaconNumberPass
# computeBaconNumberFail
# computeBaconPathPass
# computeBaconPathFail