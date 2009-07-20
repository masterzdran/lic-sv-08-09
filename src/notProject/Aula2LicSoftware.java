package notProject;

import isel.leic.usbio.UsbPort;


import isel.leic.utils.Time;

public class Aula2LicSoftware {
	private static final int SLEEP=1000;
	private static int INSHOWOUTMASK=0x80;
	private static int LEDWAYMASK=1; //Go Left

	private int shiftLeft(int i){
		return i<<1;
		
	}
	private int  shiftRight(int i){
		return i>>1;
	}
	private void show(int i){
		UsbPort.out(i);
		Time.sleep(SLEEP);
	}

public  static void main (String[] args){

	
	
	
	while((UsbPort.in()) != INSHOWOUTMASK){
		//if (UsbPort.in() )
		
	}

}

}