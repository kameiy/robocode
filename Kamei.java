package kamei;
import robocode.*;
import robocode.util.Utils;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Kamei - a robot by (your name here)
 */
public class Kamei extends AdvancedRobot
{
	/**
	 * run: Kamei's default behavior
	 */
	private static int moveAmount = 50;

	public void run() {
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        turnRadarRightRadians(Double.POSITIVE_INFINITY);
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
        
        //setTurnGunRightRadians(Utils.normalRelativeAngle(getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians()));
        //setFire(1);
		double power = getSmartFire(e.getDistance());
		double bearing = getHeadingRadians() + e.getBearingRadians();
		setTurnGunRightRadians(Utils.normalRelativeAngle((bearing + Math.asin(e.getVelocity() * Math.sin(e.getHeadingRadians() - bearing) / Rules.getBulletSpeed(power))) - getGunHeadingRadians()));
		setFire(power);
        
        setTurnRightRadians(e.getBearingRadians() + Math.PI/2);
        setAhead(moveAmount);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		moveAmount = -moveAmount;	
	}
	
/**
	 * smartFire:  Custom fire method that determines firepower based on distance.
	 *
	 * @param robotDistance the distance to the robot to fire at
	 */
	public double getSmartFire(double robotDistance) {
		if (robotDistance > 200 || getEnergy() < 15) {
			return 1;
		} else if (robotDistance > 50) {
			return(2);
		} else {
			return(3);
		}
	}
}
