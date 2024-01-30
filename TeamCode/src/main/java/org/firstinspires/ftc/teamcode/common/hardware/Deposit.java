package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingCRServo;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingDcMotorEX;

import java.util.function.DoubleSupplier;

public class Deposit extends Subsystem {
    private DcMotorEx leftMotor, rightMotor;
    private CRServo depositServo;
    public Deposit(@NonNull OpModeEX opModeEX) {
        super(opModeEX);
    }

    public void manualControl(DoubleSupplier d){
        opModeEX.telemetry.addLine("manualcontrol");
        leftMotor.setPower(d.getAsDouble());
        rightMotor.setPower(d.getAsDouble());
    }

    public Command manualControlCommand(DoubleSupplier controller){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    manualControl(controller);
                })
                .setFinish(()->false);
    }

    public void manualDepositControl(DoubleSupplier d){
        depositServo.setPower(d.getAsDouble());
    }
    public Command manualDepositControlCommand(DoubleSupplier controller){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    manualDepositControl(controller);
                })
                .setFinish(()->false);
    }


    public Command stop(){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    leftMotor.setPower(0);
                    rightMotor.setPower(0);
                    depositServo.setPower(0);
                })
                .setFinish(()->false);
    }

    public void setZeroMode(DcMotor.ZeroPowerBehavior zpb){
        leftMotor.setZeroPowerBehavior(zpb);
        rightMotor.setZeroPowerBehavior(zpb);
    }

    @Override
    public void init() {
        leftMotor = new CachingDcMotorEX(opModeEX.hardwareMap.get(DcMotorEx.class, "dLeft"));
        rightMotor = new CachingDcMotorEX(opModeEX.hardwareMap.get(DcMotorEx.class, "dRight"));

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        depositServo = new CachingCRServo(opModeEX.hardwareMap.get(CRServo.class, "dServo"));
        depositServo.setDirection(DcMotorSimple.Direction.REVERSE);

        //setDefaultCommand(stop());
    }

    @Override
    public void periodic() {

    }

    @Override
    public void defaultCommandExecute() {

    }

    @Override
    public void close() {
        stop();
    }
}
