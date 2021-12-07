package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;

@Config
public class  Mechanisms {
    // Init Objects
    public ElapsedTime runtime = new ElapsedTime();

    // Init Objects: DcMotorEx
    public DcMotorEx linearSlideRight, linearSlideLeft;

    public DcMotorEx intake;

    // Init Objects: CRServo
    private CRServo carousel;

    // Init Objects: Servo
    public Servo cubeHold;

    // Servo Positions
    public static double CUBE_OPEN_VALUE = 0.5;
    public static double CUBE_CLOSE_VALUE = 1;

    // Power Enum
    public enum slidePositions {
        IN, OUT, MED,
    }

    public enum intakeState {
        IN, OUT, OFF
    }

    public enum carouselState {
        ON, OFF
    }

    // cubeHold Enum
    public enum cubeHoldState {
        OPEN, CLOSE
    }

    // Power Values
    public static double INTAKE_POWER = 1;
    public static double CAROUSEL_POWER = 1;

    private static double OFF_POWER = 0;

    // Constructor
    public Mechanisms(HardwareMap hardwareMap) {
        //  Hardware mapping: DcMotorEx
        linearSlideRight = hardwareMap.get(DcMotorEx.class, "linearSlideRight");
        linearSlideLeft = hardwareMap.get(DcMotorEx.class, "linearSlideLeft");
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        //  Hardware mapping: CRServo
        carousel = hardwareMap.get(CRServo.class, "carousel");

        //  Hardware mapping: Servo
        cubeHold = hardwareMap.get(Servo.class, "cubeHold");


        // Set Directions
        intake.setDirection(DcMotor.Direction.REVERSE);


        // Set modes
        linearSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        linearSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Init initial Positions
        cubeHoldState(cubeHold.CLOSE);


    }

    public void cubeHoldControl(cubeHoldState pos) {
        switch (pos) {
            case OPEN:
                cubeHold.setPosition(CUBE_OPEN_VALUE);
                break;
            case CLOSE:
                cubeHold.setPosition(CUBE_CLOSE_VALUE);
                break;
        }
    }

    public void runIntake(intakeState state) {
        switch (state) {
            case IN:
                intake.setPower(INTAKE_POWER);
                break;
            case OUT:
                intake.setPower(-INTAKE_POWER);
                break;
            default:
                intake.setPower(OFF_POWER);
        }
    }

    public void carouselControl(carouselState pos) {
        switch (pos) {
            case ON:
                carousel.setPower(CAROUSEL_POWER);
                break;
            case OFF:
                carousel.setPower(OFF_POWER);
                break;
            default:
                carousel.setPower(OFF_POWER);
        }
    }

    // Wait function that doesn't interrupt program runtime, uses elapsed time
    public void wait(int milliseconds) {
        double currTime = runtime.milliseconds();
        double waitUntil = currTime + milliseconds;
        while (runtime.milliseconds() < waitUntil) {
            // remain empty
        }
    }
}