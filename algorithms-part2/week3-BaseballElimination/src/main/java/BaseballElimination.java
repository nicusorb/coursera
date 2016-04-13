import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
    private final int[] wins, loss, remaining;
    private final int[][] gamesToPlay;
    private final Map<String, Integer> teamNameToId = new LinkedHashMap<>();
    private final Map<Integer, String> teamIdToName = new LinkedHashMap<>();
    private final int nbOfGamesRemainingToPlay;
    private final int nbOfVertices;
    private final int sourceVertex;
    private final int sinkVertex;
    private Map<Integer, Integer> vertexToTeamId;
    private FlowNetwork flowNetwork;
    private FordFulkerson fordFulkerson;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int nbOfTeams = in.readInt();
        wins = new int[nbOfTeams];
        loss = new int[nbOfTeams];
        remaining = new int[nbOfTeams];
        gamesToPlay = new int[nbOfTeams][nbOfTeams];

        readTeamsData(in, nbOfTeams);

        nbOfGamesRemainingToPlay = (numberOfTeams() - 2) * (numberOfTeams() - 1) / 2;
        nbOfVertices = 1 + nbOfGamesRemainingToPlay + (numberOfTeams() - 1) + 1;
        sourceVertex = 0;
        sinkVertex = nbOfVertices - 1;
    }

    // number of teams
    public int numberOfTeams() {
        return teamNameToId.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teamNameToId.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return wins[getTeamId(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return loss[getTeamId(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remaining[getTeamId(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int team1Id = getTeamId(team1);
        int team2Id = getTeamId(team2);
        return gamesToPlay[team1Id][team2Id];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        int teamId = getTeamId(team);

        for (int i = 0; i < numberOfTeams(); i++) {
            if (teamId != i && wins[i] > wins[teamId] + remaining[teamId])
                return true;
        }

        createFlowNetwork(teamId);
        fordFulkerson = new FordFulkerson(flowNetwork, sourceVertex, sinkVertex);

        for (FlowEdge flowEdge : flowNetwork.adj(sourceVertex)) {
            if (flowEdge.capacity() - flowEdge.flow() != 0) {
                return true;
            }
        }
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        List<String> certificate = new LinkedList<>();
        int teamId = getTeamId(team);

        for (int i = 0; i < numberOfTeams(); i++) {
            if (teamId != i && wins[i] > wins[teamId] + remaining[teamId]) {
                certificate.add(teamIdToName.get(i));
                return certificate;
            }
        }

        if (isEliminated(team)) {
            for (FlowEdge flowEdge : flowNetwork.adj(sourceVertex)) {
                if (flowEdge.capacity() - flowEdge.flow() != 0) {
                    for (int v : vertexToTeamId.keySet()) {
                        if (fordFulkerson.inCut(v))
                            certificate.add(teamIdToName.get(vertexToTeamId.get(v)));
                    }
                    return certificate;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    private void readTeamsData(In in, int nbOfTeams) {
        int teamId = 0;
        for (int k = 0; k < nbOfTeams; k++) {
            String teamName = in.readString();
            teamNameToId.put(teamName, teamId);
            teamIdToName.put(teamId, teamName);
            wins[teamId] = in.readInt();
            loss[teamId] = in.readInt();
            remaining[teamId] = in.readInt();

            for (int i = 0; i < nbOfTeams; i++) {
                gamesToPlay[teamId][i] = in.readInt();
            }
            teamId++;
        }
    }

    private int getTeamId(String team) {
        Integer teamId = teamNameToId.get(team);
        if (teamId == null)
            throw new IllegalArgumentException();
        return teamId;
    }

    private void createFlowNetwork(int teamId) {
        flowNetwork = new FlowNetwork(nbOfVertices);
        Map<Integer, Integer> teamIdToVertex = new LinkedHashMap<>();
        vertexToTeamId = new LinkedHashMap<>();

        for (int i = 0, increment = 0; i < numberOfTeams(); i++) {
            if (i != teamId) {
                int vertexId = nbOfGamesRemainingToPlay + 1 + increment;
                teamIdToVertex.put(i, vertexId);
                vertexToTeamId.put(vertexId, i);
                increment++;
            }
        }

        int vertexId = 1;
        for (int i = 0; i < gamesToPlay.length; i++) {
            for (int j = i + 1; j < gamesToPlay[i].length; j++) {
                if (i != teamId && j != teamId) {
                    flowNetwork.addEdge(new FlowEdge(sourceVertex, vertexId, gamesToPlay[i][j]));
                    int firstTeamId = teamIdToVertex.get(i);
                    flowNetwork.addEdge(new FlowEdge(vertexId, firstTeamId, Double.POSITIVE_INFINITY));
                    int secondTeamId = teamIdToVertex.get(j);
                    flowNetwork.addEdge(new FlowEdge(vertexId, secondTeamId, Double.POSITIVE_INFINITY));
                    vertexId++;
                }
            }
        }

        for (int teamVertexId : teamIdToVertex.values()) {
            int capacity = wins[teamId] + remaining[teamId] - wins[vertexToTeamId.get(teamVertexId)];
            if (capacity < 0)
                capacity = 0;
            flowNetwork.addEdge(new FlowEdge(teamVertexId, sinkVertex, capacity));
        }
    }
}