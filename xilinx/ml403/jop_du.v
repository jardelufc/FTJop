//
// Debug unit
//

module jop_du(
	// RISC Internal Interface
	clk, rst,
	dcpu_cycstb_i, dcpu_we_i, dcpu_adr_i, dcpu_dat_lsu,
	dcpu_dat_dc, icpu_cycstb_i,
	ex_freeze, branch_op, ex_insn, id_pc,
	spr_dat_npc, rf_dataw,
	du_dsr, du_stall, du_addr, du_dat_i, du_dat_o,
	du_read, du_write, du_except, du_hwbkpt,
	spr_cs, spr_write, spr_addr, spr_dat_i, spr_dat_o,

	// External Debug Interface
	dbg_stall_i, dbg_ewt_i,	dbg_lss_o, dbg_is_o, dbg_wp_o, dbg_bp_o,
	dbg_stb_i, dbg_we_i, dbg_adr_i, dbg_dat_i, dbg_dat_o, dbg_ack_o
);

//parameter dw = `OR1200_OPERAND_WIDTH;
//parameter aw = `OR1200_OPERAND_WIDTH;

//
// I/O
//

//
// RISC Internal Interface
//
input				clk;		// Clock
input				rst;		// Reset
input				dcpu_cycstb_i;	// LSU status
input				dcpu_we_i;	// LSU status
input	[31:0]			dcpu_adr_i;	// LSU addr
input	[31:0]			dcpu_dat_lsu;	// LSU store data
input	[31:0]			dcpu_dat_dc;	// LSU load data
//input	[`OR1200_FETCHOP_WIDTH-1:0]	icpu_cycstb_i;	// IFETCH unit status
input				ex_freeze;	// EX stage freeze
//input	[`OR1200_BRANCHOP_WIDTH-1:0]	branch_op;	// Branch op
//input	[dw-1:0]		ex_insn;	// EX insn
input	[31:0]			id_pc;		// insn fetch EA
input	[31:0]			spr_dat_npc;	// Next PC (for trace)
input	[31:0]			rf_dataw;	// ALU result (for trace)
//output	[`OR1200_DU_DSR_WIDTH-1:0]     du_dsr;		// DSR
output				du_stall;	// Debug Unit Stall
//output	[aw-1:0]		du_addr;	// Debug Unit Address
//input	[dw-1:0]		du_dat_i;	// Debug Unit Data In
//output	[dw-1:0]		du_dat_o;	// Debug Unit Data Out
output				du_read;	// Debug Unit Read Enable
output				du_write;	// Debug Unit Write Enable
input	[12:0]			du_except;	// Exception masked by DSR
output				du_hwbkpt;	// Cause trap exception (HW Breakpoints)
input				spr_cs;		// SPR Chip Select
input				spr_write;	// SPR Read/Write
//input	[aw-1:0]		spr_addr;	// SPR Address
//input	[dw-1:0]		spr_dat_i;	// SPR Data Input
//output	[dw-1:0]		spr_dat_o;	// SPR Data Output

//
// External Debug Interface
//
input			dbg_stall_i;	// External Stall Input
input			dbg_ewt_i;	// External Watchpoint Trigger Input
output	[3:0]		dbg_lss_o;	// External Load/Store Unit Status
output	[1:0]		dbg_is_o;	// External Insn Fetch Status
output	[10:0]		dbg_wp_o;	// Watchpoints Outputs
output			dbg_bp_o;	// Breakpoint Output
input			dbg_stb_i;      // External Address/Data Strobe
input			dbg_we_i;       // External Write Enable
//input	[aw-1:0]	dbg_adr_i;	// External Address Input
//input	[dw-1:0]	dbg_dat_i;	// External Data Input
//output	[dw-1:0]	dbg_dat_o;	// External Data Output
output			dbg_ack_o;	// External Data Acknowledge (not WB compatible)



assign du_stall = dbg_stall_i;

endmodule
