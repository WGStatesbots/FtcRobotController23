package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "tune")
public class tune extends LinearOpMode {
    robot robot;
    @Override
    public void runOpMode() {
        robot = new robot(this);
        robot.setDTMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        while(!gamepad1.a&&opModeIsActive()){telemetry.addLine("Push 10 inches then press a");
            telemetry.update();}
        telemetry.addData("DT IN per Tick = ", (robot.leftBack.getCurrentPosition()+robot.leftFront.getCurrentPosition()+robot.rightBack.getCurrentPosition()+robot.rightFront.getCurrentPosition())/40);
        telemetry.update();
        while(!gamepad1.a&&opModeIsActive()){telemetry.addLine("Raise slides to max then press a");
            telemetry.update();}
        telemetry.addData("DT IN per Tick = ", (robot.slideLeft.getCurrentPosition()+robot.slideRight.getCurrentPosition())/2);
        telemetry.update();
    }
}
