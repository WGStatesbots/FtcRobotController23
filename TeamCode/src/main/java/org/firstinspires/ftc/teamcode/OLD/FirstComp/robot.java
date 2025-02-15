package org.firstinspires.ftc.teamcode.OLD.FirstComp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class robot{
    int tickperindt = 200;
    public DcMotorEx leftBack, leftFront, rightBack, rightFront, intake, slideLeft, slideRight, hang;

    LinearOpMode OpMode;

    public Servo servo;
    public int targetHang;

    public robot(LinearOpMode opMode){
        OpMode=opMode;
        leftBack = opMode.hardwareMap.get(DcMotorEx.class, "leftRear");
        leftFront = opMode.hardwareMap.get(DcMotorEx.class, "leftFront");
        rightBack = opMode.hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = opMode.hardwareMap.get(DcMotorEx.class, "rightFront");
        intake = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        slideLeft = opMode.hardwareMap.get(DcMotorEx.class, "slideLeft");
        slideRight = opMode.hardwareMap.get(DcMotorEx.class, "slideRight");
        hang = opMode.hardwareMap.get(DcMotorEx.class, "hang");
        servo = opMode.hardwareMap.get(Servo.class, "servo");

        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setSlidePosition(0);

        slideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hang.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hang.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hang.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setSlidePosition(int position){
        slideLeft.setTargetPosition(position);
        slideRight.setTargetPosition(position);
        OpMode.telemetry.addLine(String.valueOf(slideLeft.getCurrentPosition())+" "+String.valueOf(slideRight.getCurrentPosition()));
    }

    public void move(double x, double y, double z){
        leftFront.setPower(y + x + z);
        leftBack.setPower(y - x + z);
        rightFront.setPower(y - x - z);
        rightBack.setPower(y + x - z);
        OpMode.telemetry.addLine(String.valueOf(x)+" "+String.valueOf(y)+String.valueOf(z));
    }

    public void move(double x, double y, double r, double denom){
        double deno = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);
        leftFront.setPower((y + x + r)/deno);
        leftBack.setPower((y - x + r)/deno);
        rightFront.setPower((y - x - r)/deno);
        rightBack.setPower((y + x - r)/deno);
        OpMode.telemetry.addLine(String.valueOf(x)+" "+String.valueOf(y)+String.valueOf(r));
    }
    
    public void setDTMode(DcMotor.RunMode runMode){
        leftBack.setMode(runMode);
        leftFront.setMode(runMode);
        rightBack.setMode(runMode);
        rightFront.setMode(runMode);
    }
    public void setDTPower(double pwr){
        leftBack.setPower(pwr);
        leftFront.setPower(pwr);
        rightBack.setPower(pwr);
        rightFront.setPower(pwr);
    }
    public void setDTTargetPosition(int tp){
        leftBack.setTargetPosition(tp);
        leftFront.setTargetPosition(tp);
        rightBack.setTargetPosition(tp);
        rightFront.setTargetPosition(tp);
        while(OpMode.opModeIsActive()&&(leftBack.isBusy()|| leftBack.isBusy() || rightBack.isBusy() || rightFront.isBusy())){}
    }

    public void setHangPosition(int pos){
        hang.setTargetPosition(pos);
        hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hang.setPower(1);
    }

    public void setSlidePower(double power){
        slideLeft.setPower(power);
        slideRight.setPower(power);
    }
}
