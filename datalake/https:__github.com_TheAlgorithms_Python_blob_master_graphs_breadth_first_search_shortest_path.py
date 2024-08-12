<body>
 """Breath First Search (BFS) can be used when finding the shortest path from a given source node to a target node in an unweighted graph. """ from __future__ import annotations graph = { "A": ["B", "C", "E"], "B": ["A", "D", "E"], "C": ["A", "F", "G"], "D": ["B"], "E": ["A", "B", "D"], "F": ["C"], "G": ["C"], } class Graph: def __init__(self, graph: dict[str, list[str]], source_vertex: str) -&gt; None: """ Graph is implemented as dictionary of adjacency lists. Also, Source vertex have to be defined upon initialization. """ self.graph = graph # mapping node to its parent in resulting breadth first tree self.parent: dict[str, str | None] = {} self.source_vertex = source_vertex def breath_first_search(self) -&gt; None: """ This function is a helper for running breath first search on this graph. &gt;&gt;&gt; g = Graph(graph, "G") &gt;&gt;&gt; g.breath_first_search() &gt;&gt;&gt; g.parent {'G': None, 'C': 'G', 'A': 'C', 'F': 'C', 'B': 'A', 'E': 'A', 'D': 'B'} """ visited = {self.source_vertex} self.parent[self.source_vertex] = None queue = [self.source_vertex] # first in first out queue while queue: vertex = queue.pop(0) for adjacent_vertex in self.graph[vertex]: if adjacent_vertex not in visited: visited.add(adjacent_vertex) self.parent[adjacent_vertex] = vertex queue.append(adjacent_vertex) def shortest_path(self, target_vertex: str) -&gt; str: """ This shortest path function returns a string, describing the result: 1.) No path is found. The string is a human readable message to indicate this. 2.) The shortest path is found. The string is in the form `v1(-&gt;v2-&gt;v3-&gt;...-&gt;vn)`, where v1 is the source vertex and vn is the target vertex, if it exists separately. &gt;&gt;&gt; g = Graph(graph, "G") &gt;&gt;&gt; g.breath_first_search() Case 1 - No path is found. &gt;&gt;&gt; g.shortest_path("Foo") Traceback (most recent call last): ... ValueError: No path from vertex: G to vertex: Foo Case 2 - The path is found. &gt;&gt;&gt; g.shortest_path("D") 'G-&gt;C-&gt;A-&gt;B-&gt;D' &gt;&gt;&gt; g.shortest_path("G") 'G' """ if target_vertex == self.source_vertex: return self.source_vertex target_vertex_parent = self.parent.get(target_vertex) if target_vertex_parent is None: msg = ( f"No path from vertex: {self.source_vertex} to vertex: {target_vertex}" ) raise ValueError(msg) return self.shortest_path(target_vertex_parent) + f"-&gt;{target_vertex}" if __name__ == "__main__": g = Graph(graph, "G") g.breath_first_search() print(g.shortest_path("D")) print(g.shortest_path("G")) print(g.shortest_path("Foo"))
</body>