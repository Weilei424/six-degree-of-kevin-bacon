# Jira Scrum Board: [Click here](https://masonw.atlassian.net/jira/software/projects/UU3311/boards/1)

# Team members:
| **Name** | **GitHub ID** | **Contact**  |
|:--------:|:-------------:|:------------:|
| Zhenxu(Mason) Wang | Weilei424 | masonw@my.yorku.ca |
| Rebal Mallick  | mallick-rebal | rebal97@my.yorku.ca |
| Arian Quader | quader-arian | arianq@my.yorku.ca |
|       |       |       |


# Robot Framework Test cases:
### addActorPass:
- [ ] 200  OK
### addActorFail:
- [ ] 400  Path missing required information / Invalid path
- [ ] 400  Request body improperly formatted
- [ ] 400  Same node added twice
### addMoviePass:
- [ ] 200  OK
### addMovieFail:
- [ ] 400  Path missing required information / Invalid path
- [ ] 400  Request body improperly formatted
- [ ] 400  Same node added twice
### addRelationshipPass:
- [x] 200  OK
### addRelationshipFail:
- [x] 400  Path missing required information / Invalid path
- [x] 400  Request body improperly formatted
- [x] 404  Actor does not exist
- [x] 404  Movie does not exist
- [x] 400  Same relationship added twice
### getActorPass:
- [ ] 200  OK
### getActorFail:
- [ ] 400  Path missing required information / Invalid path
- [ ] 404  Actor does not exist
### getMoviePass:
- [x] 200  OK
### getMovieFail:
- [x] 400  Path missing required information / Invalid path
- [x] 404  Movie does not exist
### hasRelationshipPass:
- [x] 200  OK
### hasRelationshipFail:
- [x] 400  Path missing required information / Invalid path
- [x] 404  Actor does not exist
- [x] 404  Movie does not exist
### computeBaconNumberPass:
- [ ] 200  OK
### computeBaconNumberFail:
- [ ] 400  Path missing required information / Invalid path
- [ ] 404  Actor does not exist
- [ ] 404  No path to Kevin Bacon
### computeBaconPathPass:
- [ ] 200  OK
### computeBaconPathFail:
- [ ] 400  Path missing required information / Invalid path
- [ ] 404  Actor does not exist
- [ ] 404  No path to Kevin Bacon
### newFeaturePass:
- [ ] 200  OK
### newFeatureFail:


# Database setup for testing:

## Step 1: delete old db
```
MATCH (n) DETACH DELETE n;
```

## Step 2: add nodes and relationships:
*Where (M13) is a stand alone Movie node, (A30) is a stand alone Actor node, (A22, A23, A27) -> (M11) only, (A23) -> (M12) ONLY*
```
CREATE (m1:movie {name: "M1", movieId: "0001", actors: []}),
       (m2:movie {name: "M2", movieId: "0002", actors: []}),
       (m3:movie {name: "M3", movieId: "0003", actors: []}),
       (m4:movie {name: "M4", movieId: "0004", actors: []}),
       (m5:movie {name: "M5", movieId: "0005", actors: []}),
       (m6:movie {name: "M6", movieId: "0006", actors: []}),
       (m7:movie {name: "M7", movieId: "0007", actors: []}),
       (m8:movie {name: "M8", movieId: "0008", actors: []}),
       (m9:movie {name: "M9", movieId: "0009", actors: []}),
       (m10:movie {name: "M10", movieId: "0010", actors: []}),
       (m11:movie {name: "M11", movieId: "0011", actors: []}),
       (m12:movie {name: "M12", movieId: "0012", actors: []}),
       (m13:movie {name: "M13", movieId: "0013", actors: []}),
       (ak:actor {name: "Kevin Bacon", actorId: "nm1001231"}),
       (a1:actor {name: "A1", actorId: "0001", movies: []}),
       (a2:actor {name: "A2", actorId: "0002", movies: []}),
       (a3:actor {name: "A3", actorId: "0003", movies: []}),
       (a4:actor {name: "A4", actorId: "0004", movies: []}),
       (a5:actor {name: "A5", actorId: "0005", movies: []}),
       (a6:actor {name: "A6", actorId: "0006", movies: []}),
       (a7:actor {name: "A7", actorId: "0007", movies: []}),
       (a8:actor {name: "A8", actorId: "0008", movies: []}),
       (a9:actor {name: "A9", actorId: "0009", movies: []}),
       (a10:actor {name: "A10", actorId: "0010", movies: []}),
       (a11:actor {name: "A11", actorId: "0011", movies: []}),
       (a12:actor {name: "A12", actorId: "0012", movies: []}),
       (a13:actor {name: "A13", actorId: "0013", movies: []}),
       (a14:actor {name: "A14", actorId: "0014", movies: []}),
       (a15:actor {name: "A15", actorId: "0015", movies: []}),
       (a16:actor {name: "A16", actorId: "0016", movies: []}), 
       (a17:actor {name: "A17", actorId: "0017", movies: []}),
       (a18:actor {name: "A18", actorId: "0018", movies: []}),
       (a19:actor {name: "A19", actorId: "0019", movies: []}),
       (a20:actor {name: "A20", actorId: "0020", movies: []}),
       (a21:actor {name: "A21", actorId: "0021", movies: []}),
       (a22:actor {name: "A22", actorId: "0022", movies: []}),
       (a23:actor {name: "A23", actorId: "0023", movies: []}),
       (a24:actor {name: "A24", actorId: "0024", movies: []}),
       (a25:actor {name: "A25", actorId: "0025", movies: []}),
       (a26:actor {name: "A26", actorId: "0026", movies: []}), 
       (a27:actor {name: "A27", actorId: "0027", movies: []}),
       (a28:actor {name: "A28", actorId: "0028", movies: []}),
       (a29:actor {name: "A29", actorId: "0029", movies: []}),
       (a30:actor {name: "A30", actorId: "0030", movies: []}),
       (ak)-[:ACTED_IN]->(m1),
       (a1)-[:ACTED_IN]->(m1),
       (a2)-[:ACTED_IN]->(m1),
       (a2)-[:ACTED_IN]->(m2),
       (a3)-[:ACTED_IN]->(m2),
       (a4)-[:ACTED_IN]->(m2),
       (a4)-[:ACTED_IN]->(m3),
       (a4)-[:ACTED_IN]->(m4),
       (a5)-[:ACTED_IN]->(m3),
       (a5)-[:ACTED_IN]->(m5),
       (a6)-[:ACTED_IN]->(m4),
       (a6)-[:ACTED_IN]->(m6),
       (a7)-[:ACTED_IN]->(m6),
       (a7)-[:ACTED_IN]->(m7),
       (a8)-[:ACTED_IN]->(m7),
       (a8)-[:ACTED_IN]->(m8),
       (a9)-[:ACTED_IN]->(m8),
       (a9)-[:ACTED_IN]->(m9),
       (a1)-[:ACTED_IN]->(m9),
       (a10)-[:ACTED_IN]->(m2),
       (a10)-[:ACTED_IN]->(m10),
       (a10)-[:ACTED_IN]->(m9),
       (a11)-[:ACTED_IN]->(m10),
       (a12)-[:ACTED_IN]->(m10),
       (a13)-[:ACTED_IN]->(m10),
       (a14)-[:ACTED_IN]->(m5),
       (a15)-[:ACTED_IN]->(m10),
       (a16)-[:ACTED_IN]->(m7),
       (a17)-[:ACTED_IN]->(m7),
       (a18)-[:ACTED_IN]->(m6),
       (a19)-[:ACTED_IN]->(m5),
       (a20)-[:ACTED_IN]->(m3),
       (a21)-[:ACTED_IN]->(m11),
       (a22)-[:ACTED_IN]->(m11),
       (a23)-[:ACTED_IN]->(m12),
       (a24)-[:ACTED_IN]->(m4),
       (a25)-[:ACTED_IN]->(m2),
       (a26)-[:ACTED_IN]->(m8),
       (a27)-[:ACTED_IN]->(m11),
       (a28)-[:ACTED_IN]->(m4),
       (a29)-[:ACTED_IN]->(m5)
RETURN m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, ak, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29, a30;
```

## Step 3: query entire graph
```
MATCH (n) RETURN n LIMIT 100;
```
