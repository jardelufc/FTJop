

// synopsys translate_off
//`include "timescale.v"
// synopsys translate_on

//`define DBG_CPU0_SUPPORTED         1
//`define DBG_WISHBONE_SUPPORTED     1

`include "dbg_cpu_defines.v"

module soc(



	clk,
	

	sram_clk,
	sram_feedback_clk,
	
	sram_addr, 
	
	sram_we_n, 
	sram_oe_n, 

	sram_data, 
	
	sram_bw0,
	sram_bw1,
	
	sram_bw2,
	sram_bw3,
	
	sram_adv_ld_n,
	//--sram_mode,
	sram_cen,
	sram_cen_test,
	sram_zz,
	cpu_stall_i,

   ser_txd,
	ser_rxd,
	wd,
	myled, myled2,
   

	
	reset,
   

   //JTAG ports
   jtag_tdi,jtag_tms,jtag_tck,
   jtag_tdo

   //AIN,BIN,COUT
	   //AIN2,BIN2,COUT2

   );




	output sram_clk;
	output sram_feedback_clk;
	
	output [20:0] sram_addr;
	
	output sram_we_n;
	output sram_oe_n;

	inout [31:0] sram_data;
	
	output sram_bw0;
	output sram_bw1;
	
	output sram_bw2;
	output sram_bw3;
	
	output sram_adv_ld_n;
	//sram_mode : out std_logic;
	output sram_cen;
	output sram_cen_test;
	output sram_zz;

	input cpu_stall_i;




	output ser_txd;
	input ser_rxd;		

	output wd;


   input         clk; /* synthesis xc_clockbuftype = "BUFGDLL" */
   input         reset;
//   input         clk;

   input         jtag_tdi;
   input         jtag_tms;
   input         jtag_tck;
   output        jtag_tdo;
   
//   input AIN;
//   input BIN;
//   output COUT;
	output myled;
	output myled2;

   //input AIN2;
   //input BIN2;
   //output COUT2;

//
// TAP<->dbg_interface
//      
wire debug_select;
	     
wire shift_dr;
wire capture_dr;
wire pause_dr;
wire update_dr;
wire extest;
wire bs_AtoB;
wire bs_BtoC;
wire c_from_core;
wire bs_tdo;



wire debug_tdi;
wire debug_tdo;		     

//
// Debug <-> RISC wires
//
wire  [3:0]   dbg_lss;
wire  [1:0]   dbg_is;
wire  [10:0]  dbg_wp;
wire          dbg_bp;
wire  [31:0]  dbg_dat_dbg;
wire  [31:0]  dbg_dat_risc;
wire  [31:0]  dbg_adr;
wire          dbg_ewt;
wire          dbg_stall;
wire          dbg_we;
wire          dbg_stb; 
wire          dbg_ack;

jop jop (

	.clk (clk),


	.ser_txd (ser_txd),
	.ser_rxd (ser_rxd),

	.wd(wd),

	.sram_clk(sram_clk),
	.sram_feedback_clk(sram_feedback_clk),
	
	.sram_addr(sram_addr), 
	
	.sram_we_n(sram_we_n), 
	.sram_oe_n(sram_oe_n), 

	.sram_data(sram_data), 
	
	.sram_bw0(sram_bw0),
	.sram_bw1(sram_bw1),
	
	.sram_bw2(sram_bw2),
	.sram_bw3(sram_bw3),
	
	.sram_adv_ld_n(sram_adv_ld_n),
	//--sram_mode,
	.sram_cen(sram_cen),
	.sram_cen_test(sram_cen_test),
	.sram_zz(sram_zz),
	
	.cpu_stall_i(dbg_stall),
	
   .myled(myled),
	.myled2(myled2)



);

//`ifdef DBG_CPU0_SUPPORTED
/*cpu_behavioral i_cpu0_behavioral
                   (
                    // CPU signals             
				  
						  
						   .cpu_clk_i  ( clk ),
							.cpu_addr_i ( dbg_adr ),
							.cpu_data_o ( dbg_dat_risc ),
							.cpu_data_i ( dbg_dat_dbg ),
							.cpu_bp_o   ( dbg_bp ),
							.cpu_stall_i( dbg_stall ),
							.cpu_stb_i  ( dbg_stb ),
							.cpu_we_i   ( dbg_we ),
							.cpu_ack_o  ( dbg_ack),
							.cpu_rst_i ( ),
							.AIN2(AIN2),
							.BIN2(BIN2),
							.COUT2(COUT2)


                   );
//`endif

*/
//
// Instantiation of the development i/f
//
dbg_top dbg_top  (

	// JTAG pins
      .tck_i	( jtag_tck ),
      .tdi_i	( debug_tdi ),
      .tdo_o	( debug_tdo ),
      .rst_i	( reset ),

	// Boundary Scan signals
      .shift_dr_i  ( shift_dr ),
      .pause_dr_i  ( pause_dr ),
      .update_dr_i ( update_dr ),

      .debug_select_i( debug_select),
	// WISHBONE common
      //.wb_clk_i   ( clk ),

      // WISHBONE master interface
      //.wb_adr_o  ( wb_dm_adr_o ),
      //.wb_dat_i  ( wb_dm_dat_i ),
      //.wb_dat_o  ( wb_dm_dat_o ),
      //.wb_sel_o  ( wb_dm_sel_o ),
      //.wb_we_o   ( wb_dm_we_o  ),
      //.wb_stb_o  ( wb_dm_stb_o ),
      //.wb_cyc_o  ( wb_dm_cyc_o ),
      //.wb_cab_o  ( wb_dm_cab_o ),
      //.wb_ack_i  ( wb_dm_ack_i ),
      //.wb_err_i  ( wb_dm_err_i ),
      //.wb_cti_o  ( ),
      //.wb_bte_o  ( ),
   
      // RISC signals
      .cpu0_clk_i  ( clk ),
      .cpu0_addr_o ( dbg_adr ),
      .cpu0_data_i ( dbg_dat_risc ),
      .cpu0_data_o ( dbg_dat_dbg ),
      .cpu0_bp_i   ( dbg_bp ),
      .cpu0_stall_o( dbg_stall ),
      .cpu0_stb_o  ( dbg_stb ),
      .cpu0_we_o   ( dbg_we ),
      .cpu0_ack_i  ( dbg_ack)
      //.cpu0_rst_o  ( )

);


//porta_and porta_and (
//
//   .A (AIN),
//   .B (BIN),
//   .C (c_from_core)
//);



/*
OutputCell OutputCellC (    //FromCore, FromPreviousBSCell, CaptureDR, ShiftDR, UpdateDR, extest, TCK, ToNextBSCell, FromOutputEnable, istatedPin);
   .FromCore (c_from_core),
   .FromPreviousBSCell (bs_BtoC),
   .CaptureDR (capture_dr),
   .ShiftDR (shift_dr),
   .UpdateDR (update_dr),
   //.extest (extest),
	.extest (1'b0),
   .TCK (jtag_tck),
   .FromOutputEnable (1'b1),
 //  .ToNextBSCell (bs_tdo),
   .TristatedPin   (COUT)
   );


   InputCell InputCellB (          // InputPin, FromPreviousBSCell, CaptureDR, ShiftDR, TCK, ToNextBSCell);
   .InputPin (BIN),
   .FromPreviousBSCell (bs_AtoB),
   .CaptureDR (capture_dr),
   .ShiftDR (shift_dr),
   .TCK (jtag_tck),
   .ToNextBSCell(bs_BtoC)
);

InputCell InputCellA  (        // InputPin, FromPreviousBSCell, CaptureDR, ShiftDR, TCK, ToNextBSCell);
   .InputPin (AIN),
//   .FromPreviousBSCell (jtag_tdi ),
   .CaptureDR (capture_dr),
   .ShiftDR (shift_dr),
   .TCK (jtag_tck),
   .ToNextBSCell(bs_AtoB)
);*/

//
// JTAG TAP controller instantiation
//
//
// JTAG TAP controller instantiation
//
tap_top tap_top(
   
   .tms_pad_i	( jtag_tms ),
   .tck_pad_i	( jtag_tck ),
   .trst_pad_i	( reset ),
   .tdi_pad_i	( jtag_tdi ),
   .tdo_pad_o	( jtag_tdo ),
   .tdo_padoe_o   ( ),
   
   
   .shift_dr_o   ( shift_dr ),
   .pause_dr_o   ( pause_dr ), 
   .update_dr_o  ( update_dr ),
   .capture_dr_o ( ),

   .extest_select_o        ( ), 
   .sample_preload_select_o( ),
   .mbist_select_o         ( ),
   .debug_select_o         ( debug_select ),
                
   .tdo_o( debug_tdi ), 
               
   .debug_tdi_i    ( debug_tdo ),   // from debug module
   .bs_chain_tdi_i ( 1'b0),        // from Boundary Scan Chain
   .mbist_tdi_i    ( 1'b0)         // from Mbist Chain
);




   
endmodule

