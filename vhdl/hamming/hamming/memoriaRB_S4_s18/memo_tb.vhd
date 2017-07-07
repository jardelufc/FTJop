
--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   15:26:22 12/16/2008
-- Design Name:   jbc
-- Module Name:   C:/LESC/Hamming/hamming(15-09)/memoriaRB_S4_s18/memo_tb.vhd
-- Project Name:  memo
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: jbc
--
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends 
-- that these types always be used for the top-level I/O of a design in order 
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.ALL;

ENTITY memo_tb_vhd IS
END memo_tb_vhd;

ARCHITECTURE behavior OF memo_tb_vhd IS 

	-- Component Declaration for the Unit Under Test (UUT)
	COMPONENT bhc
	PORT(
		clk : IN std_logic;
		data : IN std_logic_vector(15 downto 0);
		rd_addr : IN std_logic_vector(11 downto 0);
		wr_addr : IN std_logic_vector(9 downto 0);
		wr_en : IN std_logic;          
		q : OUT std_logic_vector(3 downto 0)
		);
	END COMPONENT;

	--Inputs
	SIGNAL clk :  std_logic := '0';
	SIGNAL wr_en :  std_logic := '0';
	SIGNAL data :  std_logic_vector(15 downto 0) := (others=>'0');
	SIGNAL rd_addr :  std_logic_vector(11 downto 0) := (others=>'0');
	SIGNAL wr_addr :  std_logic_vector(9 downto 0) := (others=>'0');

	--Outputs
	SIGNAL q :  std_logic_vector(3 downto 0);

BEGIN

	-- Instantiate the Unit Under Test (UUT)
	uut: jbc PORT MAP(
		clk => clk,
		data => data,
		rd_addr => rd_addr,
		wr_addr => wr_addr,
		wr_en => wr_en,
		q => q
	);


	clock : process
	BEGIN
	
	clk <= '1';
	wait for 50 ns;
	clk <= '0';
	wait for 50 ns;
	
	end process;

	tb : PROCESS
	BEGIN

		-- Wait 100 ns for global reset to finish
		wait for 100 ns;

	data <= x"0156";
	wr_addr <= "0000011000";
	wr_en <= '1';
	wait for 100 ns;
	wr_en <= '0';
	wait for 100 ns;
	
	rd_addr <= "000001100001";
	wait for 100 ns;
	rd_addr <= "000001100000";
	wait for 100 ns;
	rd_addr <= "000001100010";
	wait for 100 ns;
	rd_addr <= "000001100011";
	wait for 100 ns;
		wait; -- will wait forever
	END PROCESS;

END;
