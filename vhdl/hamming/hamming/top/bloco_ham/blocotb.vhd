
--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   10:43:53 09/25/2008
-- Design Name:   ham_top
-- Module Name:   C:/Documents and Settings/david/LESC/Hamming/hamming(15-09)/top/bloco_ham/blocotb.vhd
-- Project Name:  bloco_ham
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: ham_top
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

ENTITY blocotb_vhd IS
END blocotb_vhd;

ARCHITECTURE behavior OF blocotb_vhd IS 

	-- Component Declaration for the Unit Under Test (UUT)
	COMPONENT ham_top
	PORT(
		clk : IN std_logic;
		rst : IN std_logic;
		en_enc : IN std_logic;
		en_dec : IN std_logic;
		data : IN std_logic_vector(31 downto 0);
		addr_rd : IN std_logic_vector(11 downto 0);
		addr_wr : IN std_logic_vector(9 downto 0);
		--ready : OUT std_logic;
		--error : OUT std_logic;
		Ham_out : OUT std_logic_vector(7 downto 0)
		);
	END COMPONENT;

	--Inputs
	SIGNAL clk :  std_logic := '0';
	SIGNAL rst :  std_logic := '0';
	SIGNAL en_enc :  std_logic := '0';
	SIGNAL en_dec :  std_logic := '0';
	SIGNAL data :  std_logic_vector(31 downto 0) := (others=>'0');
	SIGNAL addr_rd :  std_logic_vector(11 downto 0) := (others=>'0');
	SIGNAL addr_wr :  std_logic_vector(9 downto 0) := (others=>'0');

	--Outputs
	--SIGNAL ready :  std_logic;
	--SIGNAL error :  std_logic;
	SIGNAL Ham_out :  std_logic_vector(7 downto 0);

BEGIN

	-- Instantiate the Unit Under Test (UUT)
	uut: ham_top PORT MAP(
		clk => clk,
		rst => rst,
		en_enc => en_enc,
		en_dec => en_dec,		
		data => data,
		addr_rd => addr_rd,
		addr_wr => addr_wr,
		--ready => ready,
		--error => error,
		Ham_out => Ham_out
	);

	clock : process
	begin
	
		clk <= '1';
		wait for 50 ns;
		clk <= '0';
		wait for 50 ns;
		
	end process;


	tb : PROCESS
	BEGIN
		rst <= '1';

		-- Wait 100 ns for global reset to finish
		wait for 100 ns;
		rst <= '0';
		en_enc <= '1';
		data <= x"00000200";
		addr_wr <= "0000011000";
		wait for 100 ns;
		addr_wr <= "0000111000";
		data <= x"10000200";
		wait for 100 ns;
		--data_dec <= x"02";
		addr_wr <= "0100011000";
		data <= x"20000200";
		wait for 100 ns;
		en_enc <= '0';
		addr_rd <= "000011100001";
		wait for 100 ns;
		addr_rd <= "000011100000";
      wait for 100 ns;
		en_dec <= '1';
      addr_rd <= "000011100010";
      wait for 100 ns;
      addr_rd <= "000011100011";
      wait for 100 ns;
      addr_rd <= "000001100001";
		-- Place stimulus here

		wait; -- will wait forever
	END PROCESS;

END;
