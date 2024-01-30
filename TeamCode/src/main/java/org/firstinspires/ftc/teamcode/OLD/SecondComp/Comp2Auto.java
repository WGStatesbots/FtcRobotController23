package org.firstinspires.ftc.teamcode.OLD.SecondComp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OLD.FirstComp.robot;

public class Comp2Auto extends LinearOpMode {
    ElapsedTime et = new ElapsedTime();
    robot robot;
    @Override
    public void runOpMode() {
        robot = new robot(this);
        waitForStart();

        //state 1
        robot.move(.2,0.2,0);
        robot.setSlidePower(1);

        while(et.seconds()<2&&opModeIsActive()){
            telemetry.addLine("In State 1");
            telemetry.update();
        }
        et.reset();

        //state 2
        robot.move(0,0,0);
        robot.setSlidePower(0);
        robot.servo.setPosition(0);

        while(et.seconds()<2&&opModeIsActive()) {
            telemetry.addLine("In State 2");
            telemetry.update();
        }
        et.reset();

        //state 3
        robot.servo.setPosition(0.5);
        while(et.seconds()<2&&opModeIsActive()) {
            telemetry.addLine("In State 3");
            telemetry.update();
        }
        robot.slideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.slideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
}
