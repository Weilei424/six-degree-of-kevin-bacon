# Jira Scrum Board: [Click here](https://masonw.atlassian.net/jira/software/projects/UU3311/boards/1)

# Team members:
| **Name** | **GitHub ID** | **Contact**  |
|:--------:|:-------------:|:------------:|
| Zhenxu(Mason) Wang | Weilei424 | masonw@my.yorku.ca |
| Rebal Mallick  | mallick-rebal | rebal97@my.yorku.ca |
| Arian Quader | quader-arian | arianq@my.yorku.ca |
|       |       |       |


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
       (a1:actor {name: "A1", actorId: "0001"}),
       (a2:actor {name: "A2", actorId: "0002"}),
       (a3:actor {name: "A3", actorId: "0003"}),
       (a4:actor {name: "A4", actorId: "0004"}),
       (a5:actor {name: "A5", actorId: "0005"}),
       (a6:actor {name: "A6", actorId: "0006"}),
       (a7:actor {name: "A7", actorId: "0007"}),
       (a8:actor {name: "A8", actorId: "0008"}),
       (a9:actor {name: "A9", actorId: "0009"}),
       (a10:actor {name: "A10", actorId: "0010"}),
       (a11:actor {name: "A11", actorId: "0011"}),
       (a12:actor {name: "A12", actorId: "0012"}),
       (a13:actor {name: "A13", actorId: "0013"}),
       (a14:actor {name: "A14", actorId: "0014"}),
       (a15:actor {name: "A15", actorId: "0015"}),
       (a16:actor {name: "A16", actorId: "0016"}), 
       (a17:actor {name: "A17", actorId: "0017"}),
       (a18:actor {name: "A18", actorId: "0018"}),
       (a19:actor {name: "A19", actorId: "0019"}),
       (a20:actor {name: "A20", actorId: "0020"}),
       (a21:actor {name: "A21", actorId: "0021"}),
       (a22:actor {name: "A22", actorId: "0022"}),
       (a23:actor {name: "A23", actorId: "0023"}),
       (a24:actor {name: "A24", actorId: "0024"}),
       (a25:actor {name: "A25", actorId: "0025"}),
       (a26:actor {name: "A26", actorId: "0026"}), 
       (a27:actor {name: "A27", actorId: "0027"}),
       (a28:actor {name: "A28", actorId: "0028"}),
       (a29:actor {name: "A29", actorId: "0029"}),
       (a30:actor {name: "A30", actorId: "0030"}),
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
