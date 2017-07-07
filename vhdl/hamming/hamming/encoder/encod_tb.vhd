
--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   11:42:33 11/15/2008
-- Design Name:   ham_enc
-- Module Name:   C:/LESC/Hamming/hamming(15-09)/encoder/ham_encod/encod_tb.vhd
-- Project Name:  ham_encod
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: ham_enc
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

ENTITY encod_tb_vhd IS
END encod_tb_vhd;

ARCHITECTURE behavior OF encod_tb_vhd IS 

	-- Component Declaration for the Unit Under Test (UUT)
	COMPONENT ham_enc
	PORT(
		reset : IN std_logic;
		tclk : IN std_logic;
		enable : IN std_logic;
		data : IN std_logic_vector(31 downto 0);          
		ready : OUT std_logic;
		Ham_out : OUT std_logic_vector(15 downto 0)
		);
	END COMPONENT;

	--Inputs
	SIGNAL reset :  std_logic := '0';
	SIGNAL tclk :  std_logic := '0';
	SIGNAL enable :  std_logic := '0';
	SIGNAL data :  std_logic_vector(31 downto 0) := (others=>'0');

	--Outputs
	SIGNAL ready :  std_logic;
	SIGNAL Ham_out :  std_logic_vector(15 downto 0);

BEGIN

	-- Instantiate the Unit Under Test (UUT)
	uut: ham_enc PORT MAP(
		reset => reset,
		tclk => tclk,
		enable => enable,
		data => data,
		ready => ready,
		Ham_out => Ham_out
	);

   -- Clock process definitions
   tclk_process :process
   begin
		tclk <= '1';
		wait for 50 ns;
		tclk <= '0';
		wait for 50 ns;
   end process;
 

   -- Stimulus process
   ham : process
   begin		
      -- hold reset state for 100ms.
      wait for 100 ns;	

		reset <= '1';
		wait for 100 ns;
		
		reset <= '0';
		enable <= '1';
		data <= x"00000200";
		wait for 100 ns;
		
		data <= x"10000200";
		wait for 100 ns;

		data <= x"20000200";
		wait for 100 ns;
		enable <= '0';

	end process;

END;
