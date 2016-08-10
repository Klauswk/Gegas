package Etc;

public class Time 
{
	public static float deltaTime = 0;			//time it took to complete last frame
	public static long frameCount = 0; 			// Number of rendered frames
	public static float fixedTime = 0;			//The time that the last fixed update was called
	public static float fixedDeltaTime = 0.1f;	//The interval, in seconds, at which fixed updates are performed
	public static float maxDeltaTime = 0.30f;	//The maximum time that a frame can take for fixed updates
}
