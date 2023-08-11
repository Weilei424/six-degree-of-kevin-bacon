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
       (a30:actor {name: "A30", actorId: "0030", movies: []})
WITH m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, ak, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29, a30
CREATE (ak)-[:ACTED_IN]->(m1)
SET m1.actors = m1.actors + ak.actorId, ak.movies = ak.movies + m1.movieId
CREATE (a1)-[:ACTED_IN]->(m1)
SET m1.actors = m1.actors + a1.actorId, a1.movies = a1.movies + m1.movieId
CREATE (a2)-[:ACTED_IN]->(m1)
SET m1.actors = m1.actors + a2.actorId, a2.movies = a2.movies + m1.movieId
CREATE (a2)-[:ACTED_IN]->(m2)
SET m2.actors = m2.actors + a2.actorId, a2.movies = a2.movies + m2.movieId
CREATE (a3)-[:ACTED_IN]->(m2)
SET m2.actors = m2.actors + a3.actorId, a3.movies = a3.movies + m2.movieId
CREATE (a4)-[:ACTED_IN]->(m2)
SET m2.actors = m2.actors + a4.actorId, a4.movies = a4.movies + m2.movieId
CREATE (a4)-[:ACTED_IN]->(m3)
SET m3.actors = m3.actors + a4.actorId, a4.movies = a4.movies + m3.movieId
CREATE (a4)-[:ACTED_IN]->(m4)
SET m4.actors = m4.actors + a4.actorId, a4.movies = a4.movies + m4.movieId
CREATE (a5)-[:ACTED_IN]->(m3)
SET m3.actors = m3.actors + a5.actorId, a5.movies = a5.movies + m3.movieId
CREATE (a5)-[:ACTED_IN]->(m5)
SET m5.actors = m5.actors + a5.actorId, a5.movies = a5.movies + m5.movieId
CREATE (a6)-[:ACTED_IN]->(m4)
SET m4.actors = m4.actors + a6.actorId, a6.movies = a6.movies + m4.movieId
CREATE (a6)-[:ACTED_IN]->(m6)
SET m6.actors = m6.actors + a6.actorId, a6.movies = a6.movies + m6.movieId
CREATE (a7)-[:ACTED_IN]->(m6)
SET m6.actors = m6.actors + a7.actorId, a7.movies = a7.movies + m6.movieId
CREATE (a7)-[:ACTED_IN]->(m7)
SET m7.actors = m7.actors + a7.actorId, a7.movies = a7.movies + m7.movieId
CREATE (a8)-[:ACTED_IN]->(m7)
SET m7.actors = m7.actors + a8.actorId, a8.movies = a8.movies + m7.movieId
CREATE (a8)-[:ACTED_IN]->(m8)
SET m8.actors = m8.actors + a8.actorId, a8.movies = a8.movies + m8.movieId
CREATE (a9)-[:ACTED_IN]->(m8)
SET m8.actors = m8.actors + a9.actorId, a9.movies = a9.movies + m8.movieId
CREATE (a9)-[:ACTED_IN]->(m9)
SET m9.actors = m9.actors + a9.actorId, a9.movies = a9.movies + m9.movieId
CREATE (a1)-[:ACTED_IN]->(m9)
SET m9.actors = m9.actors + a1.actorId, a1.movies = a1.movies + m9.movieId
CREATE (a10)-[:ACTED_IN]->(m2)
SET m2.actors = m2.actors + a10.actorId, a10.movies = a10.movies + m2.movieId
CREATE (a10)-[:ACTED_IN]->(m10)
SET m10.actors = m10.actors + a10.actorId, a10.movies = a10.movies + m10.movieId
CREATE (a10)-[:ACTED_IN]->(m9)
SET m9.actors = m9.actors + a10.actorId, a10.movies = a10.movies + m9.movieId
CREATE (a11)-[:ACTED_IN]->(m10)
SET m10.actors = m10.actors + a11.actorId, a11.movies = a11.movies + m10.movieId
CREATE (a12)-[:ACTED_IN]->(m10)
SET m10.actors = m10.actors + a12.actorId, a12.movies = a12.movies + m10.movieId
CREATE (a13)-[:ACTED_IN]->(m10)
SET m10.actors = m10.actors + a13.actorId, a13.movies = a13.movies + m10.movieId
CREATE (a14)-[:ACTED_IN]->(m5)
SET m5.actors = m5.actors + a14.actorId, a14.movies = a14.movies + m5.movieId
CREATE (a15)-[:ACTED_IN]->(m10)
SET m10.actors = m10.actors + a15.actorId, a15.movies = a15.movies + m10.movieId
CREATE (a16)-[:ACTED_IN]->(m7)
SET m7.actors = m7.actors + a16.actorId, a16.movies = a16.movies + m7.movieId
CREATE (a17)-[:ACTED_IN]->(m7)
SET m7.actors = m7.actors + a17.actorId, a17.movies = a17.movies + m7.movieId
CREATE (a18)-[:ACTED_IN]->(m6)
SET m6.actors = m6.actors + a18.actorId, a18.movies = a18.movies + m6.movieId
CREATE (a19)-[:ACTED_IN]->(m5)
SET m5.actors = m5.actors + a19.actorId, a19.movies = a19.movies + m5.movieId
CREATE (a20)-[:ACTED_IN]->(m3)
SET m3.actors = m3.actors + a20.actorId, ak.movies = a20.movies + m3.movieId
CREATE (a21)-[:ACTED_IN]->(m11)
SET m11.actors = m11.actors + a21.actorId, a21.movies = a21.movies + m11.movieId
CREATE (a22)-[:ACTED_IN]->(m11)
SET m11.actors = m11.actors + a22.actorId, a22.movies = a22.movies + m11.movieId
CREATE (a23)-[:ACTED_IN]->(m12)
SET m12.actors = m12.actors + a23.actorId, a23.movies = a23.movies + m12.movieId
CREATE (a24)-[:ACTED_IN]->(m4)
SET m4.actors = m4.actors + a24.actorId, a24.movies = a24.movies + m4.movieId
CREATE (a25)-[:ACTED_IN]->(m2)
SET m2.actors = m2.actors + a25.actorId, a25.movies = a25.movies + m2.movieId
CREATE (a26)-[:ACTED_IN]->(m8)
SET m8.actors = m8.actors + a26.actorId, a26.movies = a26.movies + m8.movieId
CREATE (a27)-[:ACTED_IN]->(m11)
SET m11.actors = m11.actors + a27.actorId, a27.movies = a27.movies + m11.movieId
CREATE (a28)-[:ACTED_IN]->(m4)
SET m4.actors = m4.actors + a28.actorId, a28.movies = a28.movies + m4.movieId
CREATE (a29)-[:ACTED_IN]->(m5)
SET m5.actors = m5.actors + a29.actorId, a29.movies = a29.movies + m5.movieId
RETURN m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, ak, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29, a30;
```

## Step 3: query entire graph
```
MATCH (n) RETURN n LIMIT 100;
```
