----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    10:51:25 12/18/2008 
-- Design Name: 
-- Module Name:    ham_top - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity ham_top is
generic (jpc_width : integer);
port(
		clk			:in std_logic;
		rst			:in std_logic;
		en_enc		:in std_logic;--hamming encoder in.
		en_dec		:in std_logic;--hamming decoder in.
		data			:in std_logic_vector(31 downto 0);--hamming decoder in.
		addr_rd		:in std_logic_vector(jpc_width-1 downto 0);--hamming decoder in.
		addr_wr		:in std_logic_vector(jpc_width-3 downto 0);--hamming encoder in.

		--OUT
		Ham_out		:out std_logic_vector (7 downto 0)--hamming decoder out.
	 );

end ham_top;

architecture Behavioral of ham_top is

component ham_enc is
generic (jpc_width : integer);
port(
		reset 		:in std_logic; 
		tclk 			:in std_logic;
		enable		:in std_logic;
		data 			:in std_logic_vector (31 downto 0);
		addr_in		:in std_logic_vector (jpc_width-3 downto 0);

		addr_out		:out std_logic_vector (jpc_width-3 downto 0);	
		rdy_cache	:out std_logic:= '0';
		data_out		:out std_logic_vector (31 downto 0);
		Ham_out		:out std_logic_vector (15 downto 0)
);
end component;

component ham_decod is
generic (jpc_width : integer);
port(
		reset 		:in std_logic; 
		tclk 			:in std_logic;
		enable		:in std_logic;
		data 			:in std_logic_vector (12 downto 1);
		addr_in		:in std_logic_vector (jpc_width-1 downto 0);

		Ham_out		:out std_logic_vector (7 downto 0)
);
end component;

component hnc is
generic (jpc_width : integer);
port (
	clk			: in std_logic;
	data			: in std_logic_vector(15 downto 0);
	rd_addr		: in std_logic_vector(jpc_width-1 downto 0);
	wr_addr		: in std_logic_vector(jpc_width-3 downto 0);
	wr_en			: in std_logic;
	q				: out std_logic_vector(3 downto 0)
);
end component;

component jbc is
generic (jpc_width : integer);
port (
	clk			: in std_logic;
	data			: in std_logic_vector(31 downto 0);
	rd_addr		: in std_logic_vector(jpc_width-1 downto 0);
	wr_addr		: in std_logic_vector(jpc_width-3 downto 0);
	wr_en			: in std_logic;
	q				: out std_logic_vector(7 downto 0)
);
end component;

signal tclk,trst,twr_en,tdec_en		:std_logic;
signal twr_addr							:std_logic_vector(jpc_width-3 downto 0);
signal trd_addr							:std_logic_vector(jpc_width-1 downto 0);
signal tdata								:std_logic_vector(15 downto 0);
signal tdata2 								:std_logic_vector(31 downto 0);
signal dec_data							:std_logic_vector(11 downto 0);


begin

	encod: ham_enc 				generic map (jpc_width)
										PORT MAP (reset => rst, tclk => clk, enable => en_enc, data => data,
									    addr_in => addr_wr, addr_out => twr_addr, rdy_cache => twr_en,
										 data_out => tdata2, Ham_out => tdata);
	
	--decod: ham_decod 	PORT MAP (reset => rst, tclk => clk, enable => en_dec, data => dec_data,
	--									 addr_in => addr_rd,addr_out => trd_addr,Ham_out => Ham_out);
	decod: ham_decod 	
										generic map (jpc_width)
										PORT MAP (reset => rst, tclk => clk, enable => en_dec, data => dec_data,
										 addr_in => addr_rd,Ham_out => Ham_out);
	
	cache8bits: jbc		
										generic map (jpc_width)
										PORT MAP (clk => clk, data => tdata2, rd_addr => addr_rd, wr_addr => twr_addr,
										 wr_en => twr_en, q => dec_data(11 downto 4));
	
	cache4bits: hnc			
										generic map (jpc_width)
										PORT MAP (clk => clk, data => tdata, rd_addr => addr_rd, wr_addr => twr_addr,
										 wr_en => twr_en, q => dec_data(3 downto 0));
	
end Behavioral;

