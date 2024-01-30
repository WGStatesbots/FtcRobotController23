package org.firstinspires.ftc.teamcode.OLD.FirstComp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "tune")
public class tune extends LinearOpMode {
    robot robot;
    @Override
    public void runOpMode() {
        robot = new robot(this);
        robot.hang.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.hang.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while(!gamepad1.a&&opModeIsActive()){
            telemetry.addData("Winch up robot, press a", robot.hang.getCurrentPosition());
            telemetry.update();

            robot.hang.setPower(gamepad1.left_trigger);
        }
        int targetHang=robot.hang.getCurrentPosition();
        robot.setHangPosition(targetHang);
       while(opModeIsActive()){}
    }
}
