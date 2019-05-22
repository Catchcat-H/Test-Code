package game_of_life;

import java.util.Random;

public class Rules {
	private int width;  
	private int height;  
	private int[][] nowlife=null;
	Random r=new Random();
	
	public Rules(int[][] Nowlife, int height, int width) {
		this.width=width;
		this.height=height;
		this.nowlife=Nowlife;
	}
	
	public void random() {
		for(int x=0; x<height; x++)
			for(int y=0; y<width; y++) 
				if(r.nextBoolean())
					nowlife[x][y]=1;
				else
					nowlife[x][y]=0;
	}
	
	public void celllife() {
		int[][] nextlife=new int [height][width];
		for(int x=0; x<height; x++)
			for(int y=0; y<width; y++) {
				nextlife[x][y]=0;
				int nearnum=findnum(x,y);
				if (nearnum==3)  
					nextlife[x][y]=1;
				else if(nearnum==2)
					nextlife[x][y]=nowlife[x][y];
			}
		nowlife=nextlife;
	}
	
	private int findnum(int x, int y) {
		int nearnum = 0;
		if(y!=0) nearnum+=nowlife[x][y-1];
		if(y!=0 &&x!=0) nearnum+=nowlife[x-1][y-1] ;
		if(x!=0) nearnum+=nowlife[x-1][y] ;
		if(y!=width-1 &&x!=0) nearnum+=nowlife[x-1][y+1] ;
		if(y!=width-1) nearnum+=nowlife[x][y+1];
		if(y!=width-1 &&x!=height-1) nearnum+=nowlife[x+1][y+1] ;
		if(x!=height-1) nearnum+=nowlife[x+1][y] ;
		if(y!=0 &&x!=height-1) nearnum+=nowlife[x+1][y-1] ;
		return nearnum;
	}

	public void cleanNowlife(){
		for(int x=0; x<height; x++)
			for(int y=0; y<width; y++) 
					nowlife[x][y]=0;
	}
	
	public int[][] getNowlife() {
		return nowlife;
	}
	

}
