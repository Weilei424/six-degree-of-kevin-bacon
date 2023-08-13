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
${initDemoDb}    /initDemoDb    # this path is for the new feature 
${deleteAll}    /all    # this path is the setup before all test cases
${newFeature}
${KevinBaconId}       nm1001231
${KevinBacon}    Kevin Bacon
${A01}    0001
${A16}    0016
${A23}    0023
${A24}    0024
${A30}    0030
${M1}    0001
${M9}    0009
${M12}    0012
${fakeMovieId}    9000
${fakeMovieName}    nOsucHmoviE
${fakeActorId}    9876
${fakeActorName}    Void
${newActor1id}    0080
${newActor1name}    Aaron Paul
${newActor2id}    0081
${newActor2name}    The Rock
${newActor3id}    0082
${newActor3name}    Jimmy Oyang
${newMovie1id}    9001
${newMovie1name}    New Movie 1
${newMovie2id}    9002
${newMovie2name}    New Movie 2    
${movieList}    ['9001', '0001']

*** Test Cases ***
deleteAll    # this is a setup for all following test cases, all data will be deleted when this test case execute
    ${resp}=    DELETE On Session    localhost   ${deleteAll} 
    Should Be Equal As Strings    ${resp.status_code}    204

initDemoDbPass    # test case for new feature, expect 200, since database is empty (wiped by last test case)
    ${resp}=    POST On Session    localhost    ${initDemoDb}
    Should Be Equal As Strings    ${resp.status_code}    200

initDemoDbFail    
    # test case for new feature, expect 400, since there is an existing database
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    POST On Session    localhost    ${initDemoDb}

addActorPass
    # adding a new actor, this actor will be checked in getActorPass
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params1}=    Create Dictionary    name=${newActor1name}    actorId=${newActor1id}
    ${params_json1}=    Convert To String    ${params1}
    ${resp1}=    PUT On Session    localhost    ${addActor}     data=${params_json1}    headers=${headers}
    Should Be Equal As Strings    ${resp1.status_code}    200

addActorFail
    # same node/actorid added twice
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params1}=    Create Dictionary    name=${newActor2name}    actorId=${newActor1id}    # this actorid has been added in last test case
    ${params_json1}=    Convert To String    ${params1}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addActor}     data=${params_json1}    headers=${headers}

    # invalid request body (missing actorId in this case)
    ${params2}=    Create Dictionary    name=${newActor2name}    
    ${params_json2}=    Convert To String    ${params2}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addActor}     data=${params_json2}    headers=${headers}
    
    # invalid request body (invalid json item)
    ${params3}=    Create Dictionary    name=${newActor2name}    actorIddddd=${newActor2id}    
    ${params_json3}=    Convert To String    ${params3}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addActor}     data=${params_json3}    headers=${headers}

    # invalid path
    ${params3}=    Create Dictionary    name=${newActor2name}    actorId=${newActor2id}    
    ${params_json3}=    Convert To String    ${params3}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    /addActoor    data=${params_json3}    headers=${headers}

addMoviePass
    # adding a new movie, this movie will be checked in getMoviePass
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params1}=    Create Dictionary    name=${newMovie1name}    movieId=${newMovie1id}
    ${params_json1}=    Convert To String    ${params1}
    ${resp1}=    PUT On Session    localhost    ${addMovie}     data=${params_json1}    headers=${headers}
    Should Be Equal As Strings    ${resp1.status_code}    200

addMovieFail
    # same node/movieid added twice
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params1}=    Create Dictionary    name=${newMovie2name}    movieId=${newMovie1id}    # this movieid has been added in last test case
    ${params_json1}=    Convert To String    ${params1}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addMovie}     data=${params_json1}    headers=${headers}

    # invalid request body (missing movieId in this case)
    ${params2}=    Create Dictionary    name=${newMovie2name}    
    ${params_json2}=    Convert To String    ${params2}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addMovie}     data=${params_json2}    headers=${headers}
    
    # invalid request body (invalid json item)
    ${params3}=    Create Dictionary    name=${newMovie2name}    mvid=${newMovie2id}    
    ${params_json3}=    Convert To String    ${params3}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addMovie}     data=${params_json3}    headers=${headers}

    # invalid path
    ${params3}=    Create Dictionary    name=${newMovie2name}    movieId=${newMovie2id}    
    ${params_json3}=    Convert To String    ${params3}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    /addMMMMovieeee    data=${params_json3}    headers=${headers}

addRelationshipPass
    # this relationship will be checked in hasRelationshipPass
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=${newActor1id}    movieId=${newMovie1id}
    ${params_json}=    Convert To String    ${params}
    ${resp}=    PUT On Session    localhost    ${addRelationship}     data=${params_json}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addRelationshipFail
    # actorId does not exist in db
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params1}=    Create Dictionary    actorId=${fakeActorId}    movieId=${M12}
    ${params_json1}=    Convert To String    ${params1}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    PUT On Session    localhost    ${addRelationship}    data=${params_json1}    headers=${headers}

    # movieId does not exist in db
    ${params2}=    Create Dictionary    actorId=${A30}    movieId=${fakeMovieId}
    ${params_json2}=    Convert To String    ${params2}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    PUT On Session    localhost    ${addRelationship}    data=${params_json2}    headers=${headers}

    # invalid path
    ${params3}=    Create Dictionary    actorId=${A30}    movieId=${M12}
    ${params_json3}=    Convert To String    ${params3}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    /addRELATIONSHIP    data=${params_json3}    headers=${headers}

    # relationship already exists
    ${params4}=    Create Dictionary    actorIdd=${newActor1id}    movieId=${newMovie1id}
    ${params_json4}=    Convert To String    ${params4}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addRelationship}    data=${params_json4}    headers=${headers}

    # invalid json, missing item
    ${params5}=    Create Dictionary    actorIdd=${A30}    
    ${params_json5}=    Convert To String    ${params5}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    PUT On Session    localhost    ${addRelationship}    data=${params_json5}    headers=${headers}

getActorPass
    ${endpoint_with_param}=    Set Variable   ${getActor}?${actorId}=${newActor1id}
    ${resp}=    GET On Session    localhost    ${endpoint_with_param}
    Should Be Equal As Strings    ${resp.status_code}    200
    ${json}=    To Json    ${resp.content}
    Should Be Equal As Strings    ${json['actorId']}    0080
    Should Be Equal As Strings    ${json['name']}    Aaron Paul
    Should Be True    isinstance(${json['movies']}, list)     
    List Should Contain Value    ${json}    movies

getActorFail
    # actorId does not exist in db
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${endpoint_with_param1}=    Set Variable   ${getActor}?${actorId}=${fakeActorId} 
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param1}  

    # invalid path
    ${endpoint_with_param2}=    Set Variable   ${getActor}?geettttActor=${A01}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param2}    headers=${headers}


getMoviePass
    ${endpoint_with_param}=    Set Variable   ${getMovie}?${movieId}=${newMovie1id}
    ${resp}=    GET On Session    localhost    ${endpoint_with_param}
    Should Be Equal As Strings    ${resp.status_code}    200
    ${json}=    To Json    ${resp.content}
    Should Be Equal As Strings    ${json['movieId']}    9001
    Should Be Equal As Strings    ${json['name']}    New Movie 1
    Should Be True    isinstance(${json['actors']}, list)     
    List Should Contain Value    ${json}    actors


getMovieFail
    # movieId does not exist in db
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${endpoint_with_param1}=    Set Variable   ${getMovie}?${movieId}=${fakeMovieId} 
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param1}  

    # invalid path
    ${endpoint_with_param2}=    Set Variable   ${getMovie}?GETMOVIEEEEEE=${M1}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param2}    headers=${headers}

hasRelationshipPass
    ${endpoint_with_param1}=    Set Variable   ${hasRelationship}?${actorId}=${newActor1id}&${movieId}=${newMovie1id}
    ${resp1}=    GET On Session    localhost    ${endpoint_with_param1}
    Should Be Equal As Strings    ${resp1.status_code}    200
    ${json1}=    To Json    ${resp1.content}
    Should Be Equal As Strings    ${json1['movieId']}    9001
    Should Be Equal As Strings    ${json1['actorId']}    0080
    Should Be True    ${json1['hasRelationship']} 

    ${endpoint_with_param2}=    Set Variable   ${hasRelationship}?${actorId}=${A30}&${movieId}=${M1}
    ${resp2}=    GET On Session    localhost    ${endpoint_with_param2}
    Should Be Equal As Strings    ${resp2.status_code}    200
    ${json2}=    To Json    ${resp2.content}
    Should Be Equal As Strings    ${json2['movieId']}    0001
    Should Be Equal As Strings    ${json2['actorId']}    0030
    Should Be True    not ${json2['hasRelationship']} 

hasRelationshipFail
    # invalid path
    ${endpoint_with_param1}=    Set Variable   ${hasRelationship}?${actorId}=${A23}&movieIddd=${M12}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param1} 

    # movieId does not exist in db
    ${endpoint_with_param2}=    Set Variable   ${hasRelationship}?${actorId}=${A23}&${movieId}=${fakeMovieId}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param2} 

    # actorId does not exist in db
    ${endpoint_with_param3}=    Set Variable   ${hasRelationship}?${actorId}=${fakeActorId}&${movieId}=${M12}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param3} 

computeBaconNumberPass
    ${endpoint_with_param1}=    Set Variable   ${computeBaconNumber}?${actorId}=${A16}
    ${resp1}=    GET On Session    localhost    ${endpoint_with_param1}
    Should Be Equal As Strings    ${resp1.status_code}    200
    ${json1}=    To Json    ${resp1.content}
    Should Be Equal As Strings    ${json1['baconNumber']}    4

    ${endpoint_with_param2}=    Set Variable   ${computeBaconNumber}?${actorId}=${A24}
    ${resp2}=    GET On Session    localhost    ${endpoint_with_param2}
    Should Be Equal As Strings    ${resp2.status_code}    200
    ${json2}=    To Json    ${resp2.content}
    Should Be Equal As Strings    ${json2['baconNumber']}    3

    # edge case where Kevin Bacon's id is passed
    ${endpoint_with_param3}=    Set Variable   ${computeBaconNumber}?${actorId}=${KevinBaconId}
    ${resp3}=    GET On Session    localhost    ${endpoint_with_param3}
    Should Be Equal As Strings    ${resp3.status_code}    200
    ${json3}=    To Json    ${resp3.content}
    Should Be Equal As Strings    ${json3['baconNumber']}    0

computeBaconNumberFail
    # invalid path
    ${endpoint_with_param1}=    Set Variable   ${computeBaconNumber}?$actor'id=${A16}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param1} 

    # actorId does not exist in db
    ${endpoint_with_param1}=    Set Variable   ${computeBaconNumber}?${actorId}=${fakeActorId}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param1} 

    # there is no path from A30 to Kevin Bacon
    ${endpoint_with_param1}=    Set Variable   ${computeBaconNumber}?${actorId}=${A30}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param1} 

computeBaconPathPass
    ${endpoint_with_param1}=    Set Variable   ${computeBaconPath}?${actorId}=${A16}
    ${resp1}=    GET On Session    localhost    ${endpoint_with_param1}
    Should Be Equal As Strings    ${resp1.status_code}    200
    ${json1}=    To Json    ${resp1.content}
    ${expected_bacon_path1}=    Create List    0016    0007    0008    0008    0009    0009    0001    0001    nm1001231
    Lists Should Be Equal    ${json1['baconPath']}    ${expected_bacon_path1}

    # edge case where path variable is Kevin Bacon's id
    ${endpoint_with_param2}=    Set Variable   ${computeBaconPath}?${actorId}=${KevinBaconId}
    ${resp2}=    GET On Session    localhost    ${endpoint_with_param2}
    Should Be Equal As Strings    ${resp2.status_code}    200
    ${json2}=    To Json    ${resp2.content}
    ${expected_bacon_path2}=    Create List    nm1001231
    Lists Should Be Equal    ${json2['baconPath']}    ${expected_bacon_path2}

computeBaconPathFail
    # invalid path
    ${endpoint_with_param1}=    Set Variable   ${computeBaconPath}??id=${A16}
    Run Keyword And Expect Error    *400 Client Error: Bad Request*    GET On Session    localhost    ${endpoint_with_param1}
    
    # actorId does not exist in db
    ${endpoint_with_param2}=    Set Variable   ${computeBaconPath}?${actorId}=${fakeActorId}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param2}

    # actor does not have a path to Kevin Bacon
    ${endpoint_with_param2}=    Set Variable   ${computeBaconPath}?${actorId}=${A30}
    Run Keyword And Expect Error    *404 Client Error: Not Found*    GET On Session    localhost    ${endpoint_with_param2}