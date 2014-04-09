package com.ibm.sj6;

/******************************************************************************/
/* CLASS NAME  : JFiscal232.java                                              */
/*                                                                            */
/* DESCRIPTIVE NAME: RS-232 Fiscal Printer functions native interface         */
/*                                                                            */
/* PERSON RESPONSIBLE = Raul Priegue / Dario Alpern / Pablo Carloni           */
/*                                                                            */
/* FUNCTION:                                                                  */
/*           This class provides the RS-232 Fiscal Printer library functions  */
/*           access from java.                                                */
/*                                                                            */
/* JFiscal232.java - COPYRIGHT IBM CORPORATION 2002 - 2005                    */
/*                                                                            */
/* END-OF-SPECIFICATIONS ******************************************************/

/*******************************************************************************
 * RS-232 Fiscal Printer library functions access class
 * 
 * @author IBM Argentina S.A. - Fiscal Printers Microcode development team
 */

public class JFiscal232 {
	// ---------------------------------------------------------------------------
	// Native functions
	// ---------------------------------------------------------------------------

	public static int SERIAL_PORT = 1;

	// IBM 4610 RS-232 Fiscal Printer access functions
	public native int JOpenFiscalPrinterComPort(int iComPortNbr);

	public native int JOpenFiscalPrinterComPortNoReset(int iComPortNbr);

	public native int JWriteReadUSBFiscalPrinter(byte[] TxBuffer, long lTxDataLength, byte[] RxBuffer,
			long[] lRxDataLength);

	public native int JOpenFiscalPrinterComPortForDisplaySensing(int iComPortNbr);

	public native int JCloseFiscalPrinterComPort();

	public native int JCloseFiscalPrinterComPortForDisplaySensing();

	public native int JSendCommand(byte[] TxBuffer, long lTxDataLength);

	public native int JReadStatus(byte[] RxBuffer, long[] lRxDataLength);

	// Display Management functions
	public native int JStartDisplayRecognition(long lFlags, int i232Handle1, int i232Handle2);

	public native int JGetNumberOfDisplays(byte[] bNbrOfPOSSDisplays, byte[] bNbrOf232Displays, byte[] bNbrOfMonitors);

	public native int JGetExtendedNumberOfDisplays(byte[] bNbrOfPOSSDisplays, byte[] bNbrOf232Displays,
			byte[] bNbrOfUSBDisplays, byte[] bNbrOfMonitors);

	public native int JGetDriverInfo(byte[] bType, byte[] bMajorVersion, byte[] bMinorVersion);

	public native int JOpenDistributedDisplay(int iPort, int[] iIndex, Object vPortParameters);

	public native int JWriteToDistributedDisplay(int iIndex, byte[] bBuffer, int iLength);

	public native int JCloseDistributedDisplay(int iIndex);

	// Debug/Misc. functions
	public native int JSaveNonNullAsciiPackets(byte[] bBuffer, int iBufferLength);

	public native int JSaveAllAsciiPackets(byte[] bBuffer, int iBufferLength);

	public native int JGetDLLVersion(byte[] bMajor, byte[] bMinor);

	public native int JSetFiscalPrinterComPortBaudRate(long lBaudRate);

	public native int JHookMessages();

	// IBM 4610 RS-232 Fiscal Printer access library
	static {
		System.loadLibrary("fiscal232");
	}

	// Library error codes (same as in fiscal232.h)
	public static final int RC_232_OK = 0x00;
	public static final int RC_232_INTERM_STATUS_RECV = 0x01;
	public static final int RC_232_NOTHING_RECV = 0x02;
	public static final int RC_232_PLD = 0x05;
	public static final int RC_232_IN_IPL = 0x06;

	public static final int RC_232_TIMEOUT = 0x10;
	public static final int RC_232_INTERNAL_ERROR = 0x11;
	public static final int RC_232_CANNOT_INIT_COM = 0x12;
	public static final int RC_232_INVALID_COM_NBR = 0x13;
	public static final int RC_232_INVALID_LENGTH = 0x14;
	public static final int RC_232_BAD_PACKET_READ = 0x15;
	public static final int RC_232_EXCEPTION_RECEIVED = 0x16;
	public static final int RC_232_CREATE_FILE_FAIL = 0x17;
	public static final int RC_232_ALREADY_INIT = 0x18;
	public static final int RC_232_NO_INITIALIZED = 0x19;
	public static final int RC_232_CANNOT_HOOK_POSS = 0x20;
	public static final int RC_232_POSS_ALREADY_INIT = 0x21;
	public static final int RC_232_UNEXPECTED_POSS_ERROR = 0x22;
	public static final int RC_232_POSS_NOT_AVAILABLE = 0x23;
	public static final int RC_232_DISPLAY_RECOG_NOT_STARTED = 0x24;
	public static final int RC_232_DISPLAY_RECOG_ALREADY_STARTED = 0x25;
	public static final int RC_232_DISPLAY_RECOG_FAILED = 0x26;
	public static final int RC_232_INVALID_FLAGS = 0x27;
	public static final int RC_232_CANNOT_FIND_PORT_INFO = 0x28;

	public static final int RC_232_INVALID_232HANDLE1 = 0x29;
	public static final int RC_232_INVALID_232HANDLE2 = 0x2A;
	public static final int RC_232_CANNOT_OPEN_MON_DETECT_DRV = 0x2B;
	public static final int RC_232_UNSUPPORTED_FEATURE = 0x2D;
	public static final int RC_232_DISPLAY_NOT_PRESENT = 0x2E;

	public static final int RC_232_DRIVER_VERSION_IS_NOT_ACCEPTED = 0x2F;
	public static final int RC_232_CANNOT_OPEN_MONDAEM = 0x30;
	public static final int RC_232_CANNOT_FIND_USB_INFO = 0x31;
	public static final int RC_232_INVALID_MODE = 0x32;
	public static final int RC_232_UNSUPPORTED_BAUD_RATE = 0x33;

	public static final int RC_232_CANNOT_CREATE_THREAD = 0x34;
	public static final int RC_232_CANNOT_JOIN_THREAD = 0x35;

	public static final int RC_232_DETECT_ALREADY_STARTED = 0x36;
	public static final int RC_232_DETECT_START_FAILS = 0x37;
	public static final int RC_232_DETECT_NOT_STARTED = 0x38;
	public static final int RC_232_DETECT_STOP_FAILS = 0x39;
	public static final int RC_232_DETECT_THREAD_NOT_CREATED = 0x3A;
	public static final int RC_232_CANNOT_JOIN_DETECT_THREAD = 0x3B;
	public static final int RC_232_DETECT_INIT_TIMEOUT = 0x3C;

	// Displays Management constants
	public static final long RECOGNIZE_POSS_DISPLAYS = 0x01;
	public static final long RECOGNIZE_232_DISPLAYS = 0x02;
	public static final long RECOGNIZE_MONITORS = 0x04;
	public static final long RECOGNIZE_MONITORS_WITH_DRIVER = 0x04;
	public static final long DRIVE_232_DISPLAYS = 0x08;
	public static final long RECOGNIZE_USB_DISPLAYS = 0x10;
	public static final long RECOGNIZE_MONITORS_WITH_DAEMON = 0x20;

	// RS-232 Default Baud Rate
	public static final long DEFAULT_232_DISPLAYS_RATE = 9600;

}
