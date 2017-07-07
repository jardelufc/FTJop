
--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   12:13:45 11/15/2008
-- Design Name:   hamming
-- Module Name:   C:/LESC/Hamming/hamming(15-09)/decoder/ham_encod_tb.vhd
-- Project Name:  ham_decod
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: hamming
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

ENTITY ham_encod_tb_vhd IS
END ham_encod_tb_vhd;

ARCHITECTURE behavior OF ham_encod_tb_vhd IS 

	-- Component Declaration for the Unit Under Test (UUT)
	COMPONENT hamming
	PORT(
		reset : IN std_logic;
		tclk : IN std_logic;
		enable : IN std_logic;
		data : IN std_logic_vector(12 downto 1);          
		ready : OUT std_logic;
		error : OUT std_logic;
		Ham_out : OUT std_logic_vector(7 downto 0)
		);
	END COMPONENT;

	--Inputs
	SIGNAL reset :  std_logic := '0';
	SIGNAL tclk :  std_logic := '0';
	SIGNAL enable :  std_logic := '0';
	SIGNAL data :  std_logic_vector(12 downto 1) := (others=>'0');

	--Outputs
	SIGNAL ready :  std_logic;
	SIGNAL error :  std_logic;
	SIGNAL Ham_out :  std_logic_vector(7 downto 0);

BEGIN

	-- Instantiate the Unit Under Test (UUT)
	uut: hamming PORT MAP(
		reset => reset,
		tclk => tclk,
		enable => enable,
		data => data,
		ready => ready,
		error => error,
		Ham_out => Ham_out
	);

   tclk_process :process
   begin
		tclk <= '1';
		wait for 50 ns;
		tclk <= '0';
		wait for 50 ns;
   end process;
 

   -- Stimulus process
   stim_proc: process
   begin		
      -- hold reset state for 100ms.
      wait for 100 ns;	

		reset <= '1';
		wait for 100 ns;
		
		reset <= '0';
		enable <= '1';
		data <= x"025";
		wait for 100 ns;
		
		data <= x"035";
		wait for 100 ns;

		data <= x"024";
		wait for 100 ns;
		enable <= '0';
      -- insert stimulus here 

      wait;
   end process;
END;
