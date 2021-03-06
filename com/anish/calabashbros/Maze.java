package com.anish.calabashbros;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Maze {
	private class Node{
		int x;
		int y;
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	private int[][] map;
            
	//起点
	private int startX;
	private int startY;
	//终点
	private int endX;
	private int endY;
	//给出结果的第一步
	public int stepX;
	public int stepY;
	//代表上下左右四个可能走的方向
	private int[] dx = {1,0,-1,0};
	private int[] dy = {0,1,0,-1};
	public Maze(int[][] map, int startX, int startY, int endX, int endY) {
		this.map = map;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	//广度优先遍历寻找迷宫所有点的最短路径， x,y是起始点
	public void bfs() {
		Deque<Node> quene = new ArrayDeque<Node>();
		//存储每个点的前驱节点，方便打印最短路径的路线
		int[][] pre = new  int[this.map.length][this.map[0].length];
		//存储每个点的最短路径
		int[][] dis = new  int[this.map.length][this.map[0].length];
		for(int i=0; i<dis.length; i++) {
			for(int j=0; j<dis[0].length; j++) {
				dis[i][j] = 100;
			}
		}
		//将起点入队，起点的距离设为0，并标记为已访问
		quene.add(new Node(this.startX, this.startY));
		dis[this.startX][this.startY] = 0;
		map[this.startX][this.startY] = 2;
		Node temp;
		//广度优先遍历所有可访问的点，并记下每个点的最短路径和前驱节点
		while(!quene.isEmpty()) {
			temp = quene.poll();
			//尝试每个点的四个方向
			for(int i=0; i<4; i++) {
				int tx = temp.x + dx[i];
				int ty = temp.y + dy[i];
				//如果该点没有访问过，将该点入队并标记为访问过
				if(map[tx][ty] == 0) {
					//迷宫中每次只能走一步，所以距离加一
					dis[tx][ty] = dis[temp.x][temp.y] + 1;
					pre[tx][ty] = i;
					map[tx][ty] = 2;
					quene.add(new Node(tx, ty));
				}
			}
		}//到这里dis中存放的就是最短路径，下面时利用pre数组打印路径
		
		int a = this.endX;
		int b = this.endY;
		//System.out.printf("从(%d,%d)到(%d,%d)的最短距离是：%d，路线为：\n",
		//		this.startX, this.startY, a, b, dis[a][b]);
		//倒序访问最短路径的路线并入栈
		Stack<Node> stack = new Stack<Node>();
		stack.add(new Node(a, b));
		while(a != this.startX || b != this.startY) {
			int da = dx[pre[a][b]];
			int db = dy[pre[a][b]];
			a = a - da;
			b = b - db;
			stack.add(new Node(a,b));
		}
		//出栈的顺序就是从起点到终点的路线
		Node p = stack.pop();
		p = stack.pop();
		stepX = p.x;
		stepY = p.y;
		//System.out.printf("(%d,%d)",stepX,stepY);
	}
}
