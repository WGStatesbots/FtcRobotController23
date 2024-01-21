package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingCRServo;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingDcMotor;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingDcMotorEX;

import java.util.function.DoubleSupplier;

public class Deposit extends Subsystem {
    private DcMotorEx leftMotor, rightMotor;
    private CRServo depositServo;
    public Deposit(@NonNull OpModeEX opModeEX) {
        super(opModeEX);
    }

    public Command manualControl(DoubleSupplier controller){
        return new LambdaCommand()
                .setRequirements(this)
                .setRunStates(OpModeEX.OpModeEXRunStates.LOOP)
                .setInterruptible(true)
                .setExecute(()->{
                    leftMotor.setPower(controller.getAsDouble());
                    rightMotor.setPower(controller.getAsDouble());
                })
                .setFinish(()->false);
    }

    public Command manualDepositControl(DoubleSupplier controller){
        return new LambdaCommand()
                .setRequirements(this)
                .setRunStates(OpModeEX.OpModeEXRunStates.LOOP)
                .setInterruptible(true)
                .setExecute(()->{
                    depositServo.setPower(controller.getAsDouble());
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

    @Override
    public void init() {
        leftMotor = new CachingDcMotorEX(opModeEX.hardwareMap.get(DcMotorEx.class, "dLeft"));
        rightMotor = new CachingDcMotorEX(opModeEX.hardwareMap.get(DcMotorEx.class, "dRight"));

        depositServo = new CachingCRServo(opModeEX.hardwareMap.get(CRServo.class, "dServo"));
        depositServo.setDirection(DcMotorSimple.Direction.REVERSE);

        setDefaultCommand(stop());
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
