package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RR.drive.SampleMecanumDrive;
@Autonomous(name = "testOP")
public class TestOP extends LinearOpMode {
    SampleMecanumDrive dt;
    @Override
    public void runOpMode() {
        dt = new SampleMecanumDrive(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addLine(String.valueOf(dt.getExternalHeadingVelocity()));
            telemetry.update();
        }
    }
}
