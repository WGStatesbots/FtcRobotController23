package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="auto")
public class auto extends LinearOpMode {
    robot robot;
    @Override
    public void runOpMode(){
        robot=new robot(this);
        robot.rightFront.setTargetPositionTolerance(20);
        robot.leftFront.setTargetPositionTolerance(20);
        robot.rightBack.setTargetPositionTolerance(20);
        robot.leftBack.setTargetPositionTolerance(20);
        waitForStart();
        robot.setDTMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setDTTargetPosition(60*robot.tickperindt);
        robot.setDTMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.setDTPower(0.3);
        sleep(3000);
        robot.setDTTargetPosition(8* robot.tickperindt);
    }
}
