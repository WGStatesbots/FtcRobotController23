package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOP")
public class TeleOP extends LinearOpMode {
    robot robot;
    boolean intake, slide;
    int slideMax=10000, intakeMax=100;
    @Override
    public void runOpMode() throws InterruptedException {
        robot=new robot(this);
        robot.slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.slideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.slideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while(opModeIsActive()){
            telemetry.update();
            robot.move(-Math.pow(gamepad1.left_stick_x,5), Math.pow(gamepad1.left_stick_y,5), Math.pow(gamepad1.right_stick_x,5));
            double slidePower = gamepad1.right_trigger-gamepad1.left_trigger;
            robot.slideRight.setPower(slidePower);
            robot.slideLeft.setPower(slidePower);

            telemetry.addLine(String.valueOf(robot.slideLeft.getPower())+" "+String.valueOf(robot.slideRight.getPower()));

            if(gamepad1.b) robot.intake.setPower(-0.8);
            else if(gamepad1.a) robot.intake.setPower(0.8);
            else robot.intake.setPower(0);

            if(gamepad1.y) robot.servo.setPosition(0);
            else robot.servo.setPosition(0.5);
            telemetry.addLine(String.valueOf(robot.servo.getPosition()));
        }
    }
}
