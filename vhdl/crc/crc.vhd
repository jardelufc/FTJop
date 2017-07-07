--------------------------------------------------------------
-- Arquivo:  CRC.vhd                              
-- Data:  	 22/08/2008                                                      
-- Autor: 	 David Viana
--   * polynomial: (0 1 2 4 5 7 8 10 11 12 16 22 23 26 32)
--   * data width: 32
--
--
-- Variavel C guarda os resultados acumulados dos datas que entraram antes
-- será o valor final com o qual compararemos, tem como valor inicial
-- x00000000, pois xor com 0 sai como resultado o outro valor de entrada.
-----------------------------------------------------------------


library IEEE;
--library mylib;

--use mylib.txt_util.all;
use IEEE.std_logic_1164.all;
use ieee.numeric_std.all ;
use work.jop_types.all;

entity  crc is
port(
		reset 		:in std_logic; 
		tclk 			:in std_logic;
		enable		:in std_logic;
		data 			:in std_logic_vector (31 downto 0);
		bc_len		:in std_logic_vector (METHOD_SIZE_BITS-1 downto 0);	-- length of method in words
		ready			:out std_logic:= '0';
		crc_bsy		:out std_logic := '0';
		CRC_out		:out std_logic_vector (31 downto 0)
);

end crc;

architecture behavior of  crc is
	signal len 			: unsigned(METHOD_SIZE_BITS-1 downto 0);
	--signal CRC_calc	: std_logic_vector(31 downto 0):= x"00000000";
	--signal sum 			: std_logic_vector(METHOD_SIZE_BITS-1 downto 0) := "0000000001";
	signal Comp, D2	: std_logic_vector(31 downto 0):= x"00000000";
	signal Pass, Fail,flag : std_logic := '0';
	
	begin
	
	len <= unsigned(bc_len);
	
	process(data,reset)
	
    variable D: std_logic_vector(31 downto 0);
	 variable C: std_logic_vector(31 downto 0):= x"00000000";
    variable NewCRC, CompCRC: std_logic_vector(31 downto 0);
	 variable state_flag			: std_logic:= '0';
	 variable state_len 			: unsigned(METHOD_SIZE_BITS-1 downto 0);

	 begin

	 	
    D := Data;
	 flag <= '0';	
	 if(len > "0000000000") then
		state_len := len + "0000000010";
	 end if;
		
	 if((state_len < "0000000100") and (state_len > "0000000000") ) then
		state_flag := '1';
		state_len := state_len - "0000000001";
		
	 end if;
	
	 if (tclk = '1' and reset = '1') then
			NewCRC := x"00000000";
			ready <= '0';
			crc_bsy <= '0';
			--len <= "0000000001";
			
	 elsif (len = "0000000000" and flag = '0') then
		CompCRC := NewCRC;
		Comp <= CompCRC;
		D2(31 downto 24) <= D(7 downto 0);
		D2(23 downto 16) <= D(15 downto 8);
		D2(15 downto 8) <= D(23 downto 16);
		D2(7 downto 0) <= D(31 downto 24);
		flag <= '1';
			
	 elsif (enable = '1' and tclk = '1') then

    NewCRC(0) := D(31) xor D(30) xor D(29) xor D(28) xor D(26) xor D(25) xor 
                 D(24) xor D(16) xor D(12) xor D(10) xor D(9)  xor D(6)  xor 
                 D(0)  xor C(0)  xor C(6)  xor C(9)  xor C(10) xor C(12) xor 
                 C(16) xor C(24) xor C(25) xor C(26) xor C(28) xor C(29) xor 
                 C(30) xor C(31);
  
    NewCRC(1) := D(28) xor D(27) xor D(24) xor D(17) xor D(16) xor D(13) xor 
                 D(12) xor D(11) xor D(9) xor D(7) xor D(6) xor D(1) xor 
                 D(0) xor C(0) xor C(1) xor C(6) xor C(7) xor C(9) xor 
                 C(11) xor C(12) xor C(13) xor C(16) xor C(17) xor C(24) xor 
                 C(27) xor C(28);
   
    NewCRC(2) := D(31) xor D(30) xor D(26) xor D(24) xor D(18) xor D(17) xor 
                 D(16) xor D(14) xor D(13) xor D(9) xor D(8) xor D(7) xor 
                 D(6) xor D(2) xor D(1) xor D(0) xor C(0) xor C(1) xor 
                 C(2) xor C(6) xor C(7) xor C(8) xor C(9) xor C(13) xor 
                 C(14) xor C(16) xor C(17) xor C(18) xor C(24) xor C(26) xor 
                 C(30) xor C(31);
  
    NewCRC(3) := D(31) xor D(27) xor D(25) xor D(19) xor D(18) xor D(17) xor 
                 D(15) xor D(14) xor D(10) xor D(9) xor D(8) xor D(7) xor 
                 D(3) xor D(2) xor D(1) xor C(1) xor C(2) xor C(3) xor 
                 C(7) xor C(8) xor C(9) xor C(10) xor C(14) xor C(15) xor 
                 C(17) xor C(18) xor C(19) xor C(25) xor C(27) xor C(31);
  
    NewCRC(4) := D(31) xor D(30) xor D(29) xor D(25) xor D(24) xor D(20) xor 
                 D(19) xor D(18) xor D(15) xor D(12) xor D(11) xor D(8) xor 
                 D(6) xor D(4) xor D(3) xor D(2) xor D(0) xor C(0) xor 
                 C(2) xor C(3) xor C(4) xor C(6) xor C(8) xor C(11) xor 
                 C(12) xor C(15) xor C(18) xor C(19) xor C(20) xor C(24) xor 
                 C(25) xor C(29) xor C(30) xor C(31);
  
    NewCRC(5) := D(29) xor D(28) xor D(24) xor D(21) xor D(20) xor D(19) xor 
                 D(13) xor D(10) xor D(7) xor D(6) xor D(5) xor D(4) xor 
                 D(3) xor D(1) xor D(0) xor C(0) xor C(1) xor C(3) xor 
                 C(4) xor C(5) xor C(6) xor C(7) xor C(10) xor C(13) xor 
                 C(19) xor C(20) xor C(21) xor C(24) xor C(28) xor C(29);
  
    NewCRC(6) := D(30) xor D(29) xor D(25) xor D(22) xor D(21) xor D(20) xor 
                 D(14) xor D(11) xor D(8) xor D(7) xor D(6) xor D(5) xor 
                 D(4) xor D(2) xor D(1) xor C(1) xor C(2) xor C(4) xor 
                 C(5) xor C(6) xor C(7) xor C(8) xor C(11) xor C(14) xor 
                 C(20) xor C(21) xor C(22) xor C(25) xor C(29) xor C(30);
  
    NewCRC(7) := D(29) xor D(28) xor D(25) xor D(24) xor D(23) xor D(22) xor 
                 D(21) xor D(16) xor D(15) xor D(10) xor D(8) xor D(7) xor 
                 D(5) xor D(3) xor D(2) xor D(0) xor C(0) xor C(2) xor 
                 C(3) xor C(5) xor C(7) xor C(8) xor C(10) xor C(15) xor 
                 C(16) xor C(21) xor C(22) xor C(23) xor C(24) xor C(25) xor 
                 C(28) xor C(29);
  
    NewCRC(8) := D(31) xor D(28) xor D(23) xor D(22) xor D(17) xor D(12) xor 
                 D(11) xor D(10) xor D(8) xor D(4) xor D(3) xor D(1) xor 
                 D(0) xor C(0) xor C(1) xor C(3) xor C(4) xor C(8) xor 
                 C(10) xor C(11) xor C(12) xor C(17) xor C(22) xor C(23) xor 
                 C(28) xor C(31);
  
    NewCRC(9) := D(29) xor D(24) xor D(23) xor D(18) xor D(13) xor D(12) xor 
                 D(11) xor D(9) xor D(5) xor D(4) xor D(2) xor D(1) xor 
                 C(1) xor C(2) xor C(4) xor C(5) xor C(9) xor C(11) xor 
                 C(12) xor C(13) xor C(18) xor C(23) xor C(24) xor C(29);
  
    NewCRC(10) := D(31) xor D(29) xor D(28) xor D(26) xor D(19) xor D(16) xor 
                  D(14) xor D(13) xor D(9) xor D(5) xor D(3) xor D(2) xor 
                  D(0) xor C(0) xor C(2) xor C(3) xor C(5) xor C(9) xor 
                  C(13) xor C(14) xor C(16) xor C(19) xor C(26) xor C(28) xor 
                  C(29) xor C(31);
  
    NewCRC(11) := D(31) xor D(28) xor D(27) xor D(26) xor D(25) xor D(24) xor 
                  D(20) xor D(17) xor D(16) xor D(15) xor D(14) xor D(12) xor 
                  D(9) xor D(4) xor D(3) xor D(1) xor D(0) xor C(0) xor 
                  C(1) xor C(3) xor C(4) xor C(9) xor C(12) xor C(14) xor 
                  C(15) xor C(16) xor C(17) xor C(20) xor C(24) xor C(25) xor 
                  C(26) xor C(27) xor C(28) xor C(31);
  
    NewCRC(12) := D(31) xor D(30) xor D(27) xor D(24) xor D(21) xor D(18) xor 
                  D(17) xor D(15) xor D(13) xor D(12) xor D(9) xor D(6) xor 
                  D(5) xor D(4) xor D(2) xor D(1) xor D(0) xor C(0) xor 
                  C(1) xor C(2) xor C(4) xor C(5) xor C(6) xor C(9) xor 
                  C(12) xor C(13) xor C(15) xor C(17) xor C(18) xor C(21) xor 
                  C(24) xor C(27) xor C(30) xor C(31);
  
    NewCRC(13) := D(31) xor D(28) xor D(25) xor D(22) xor D(19) xor D(18) xor 
                  D(16) xor D(14) xor D(13) xor D(10) xor D(7) xor D(6) xor 
                  D(5) xor D(3) xor D(2) xor D(1) xor C(1) xor C(2) xor 
                  C(3) xor C(5) xor C(6) xor C(7) xor C(10) xor C(13) xor 
                  C(14) xor C(16) xor C(18) xor C(19) xor C(22) xor C(25) xor 
                  C(28) xor C(31);
  
    NewCRC(14) := D(29) xor D(26) xor D(23) xor D(20) xor D(19) xor D(17) xor 
                  D(15) xor D(14) xor D(11) xor D(8) xor D(7) xor D(6) xor 
                  D(4) xor D(3) xor D(2) xor C(2) xor C(3) xor C(4) xor 
                  C(6) xor C(7) xor C(8) xor C(11) xor C(14) xor C(15) xor 
                  C(17) xor C(19) xor C(20) xor C(23) xor C(26) xor C(29);
  
    NewCRC(15) := D(30) xor D(27) xor D(24) xor D(21) xor D(20) xor D(18) xor 
                  D(16) xor D(15) xor D(12) xor D(9) xor D(8) xor D(7) xor 
                  D(5) xor D(4) xor D(3) xor C(3) xor C(4) xor C(5) xor 
                  C(7) xor C(8) xor C(9) xor C(12) xor C(15) xor C(16) xor 
                  C(18) xor C(20) xor C(21) xor C(24) xor C(27) xor C(30);
  
    NewCRC(16) := D(30) xor D(29) xor D(26) xor D(24) xor D(22) xor D(21) xor 
                  D(19) xor D(17) xor D(13) xor D(12) xor D(8) xor D(5) xor 
                  D(4) xor D(0) xor C(0) xor C(4) xor C(5) xor C(8) xor 
                  C(12) xor C(13) xor C(17) xor C(19) xor C(21) xor C(22) xor 
                  C(24) xor C(26) xor C(29) xor C(30);
  
    NewCRC(17) := D(31) xor D(30) xor D(27) xor D(25) xor D(23) xor D(22) xor 
                  D(20) xor D(18) xor D(14) xor D(13) xor D(9) xor D(6) xor 
                  D(5) xor D(1) xor C(1) xor C(5) xor C(6) xor C(9) xor 
                  C(13) xor C(14) xor C(18) xor C(20) xor C(22) xor C(23) xor 
                  C(25) xor C(27) xor C(30) xor C(31);
  
    NewCRC(18) := D(31) xor D(28) xor D(26) xor D(24) xor D(23) xor D(21) xor 
                  D(19) xor D(15) xor D(14) xor D(10) xor D(7) xor D(6) xor 
                  D(2) xor C(2) xor C(6) xor C(7) xor C(10) xor C(14) xor 
                  C(15) xor C(19) xor C(21) xor C(23) xor C(24) xor C(26) xor 
                  C(28) xor C(31);
  
    NewCRC(19) := D(29) xor D(27) xor D(25) xor D(24) xor D(22) xor D(20) xor 
                  D(16) xor D(15) xor D(11) xor D(8) xor D(7) xor D(3) xor 
                  C(3) xor C(7) xor C(8) xor C(11) xor C(15) xor C(16) xor 
                  C(20) xor C(22) xor C(24) xor C(25) xor C(27) xor C(29);
  
    NewCRC(20) := D(30) xor D(28) xor D(26) xor D(25) xor D(23) xor D(21) xor 
                  D(17) xor D(16) xor D(12) xor D(9) xor D(8) xor D(4) xor 
                  C(4) xor C(8) xor C(9) xor C(12) xor C(16) xor C(17) xor 
                  C(21) xor C(23) xor C(25) xor C(26) xor C(28) xor C(30);
  
    NewCRC(21) := D(31) xor D(29) xor D(27) xor D(26) xor D(24) xor D(22) xor 
                  D(18) xor D(17) xor D(13) xor D(10) xor D(9) xor D(5) xor 
                  C(5) xor C(9) xor C(10) xor C(13) xor C(17) xor C(18) xor 
                  C(22) xor C(24) xor C(26) xor C(27) xor C(29) xor C(31);
  
    NewCRC(22) := D(31) xor D(29) xor D(27) xor D(26) xor D(24) xor D(23) xor 
                  D(19) xor D(18) xor D(16) xor D(14) xor D(12) xor D(11) xor 
                  D(9) xor D(0) xor C(0) xor C(9) xor C(11) xor C(12) xor 
                  C(14) xor C(16) xor C(18) xor C(19) xor C(23) xor C(24) xor 
                  C(26) xor C(27) xor C(29) xor C(31);
  
    NewCRC(23) := D(31) xor D(29) xor D(27) xor D(26) xor D(20) xor D(19) xor 
                  D(17) xor D(16) xor D(15) xor D(13) xor D(9) xor D(6) xor 
                  D(1) xor D(0) xor C(0) xor C(1) xor C(6) xor C(9) xor 
                  C(13) xor C(15) xor C(16) xor C(17) xor C(19) xor C(20) xor 
                  C(26) xor C(27) xor C(29) xor C(31);
  
    NewCRC(24) := D(30) xor D(28) xor D(27) xor D(21) xor D(20) xor D(18) xor 
                  D(17) xor D(16) xor D(14) xor D(10) xor D(7) xor D(2) xor 
                  D(1) xor C(1) xor C(2) xor C(7) xor C(10) xor C(14) xor 
                  C(16) xor C(17) xor C(18) xor C(20) xor C(21) xor C(27) xor 
                  C(28) xor C(30);
  
    NewCRC(25) := D(31) xor D(29) xor D(28) xor D(22) xor D(21) xor D(19) xor 
                  D(18) xor D(17) xor D(15) xor D(11) xor D(8) xor D(3) xor 
                  D(2) xor C(2) xor C(3) xor C(8) xor C(11) xor C(15) xor 
                  C(17) xor C(18) xor C(19) xor C(21) xor C(22) xor C(28) xor 
                  C(29) xor C(31);
  
    NewCRC(26) := D(31) xor D(28) xor D(26) xor D(25) xor D(24) xor D(23) xor 
                  D(22) xor D(20) xor D(19) xor D(18) xor D(10) xor D(6) xor 
                  D(4) xor D(3) xor D(0) xor C(0) xor C(3) xor C(4) xor 
                  C(6) xor C(10) xor C(18) xor C(19) xor C(20) xor C(22) xor 
                  C(23) xor C(24) xor C(25) xor C(26) xor C(28) xor C(31);
  
    NewCRC(27) := D(29) xor D(27) xor D(26) xor D(25) xor D(24) xor D(23) xor 
                  D(21) xor D(20) xor D(19) xor D(11) xor D(7) xor D(5) xor 
                  D(4) xor D(1) xor C(1) xor C(4) xor C(5) xor C(7) xor 
                  C(11) xor C(19) xor C(20) xor C(21) xor C(23) xor C(24) xor 
                  C(25) xor C(26) xor C(27) xor C(29);
  
    NewCRC(28) := D(30) xor D(28) xor D(27) xor D(26) xor D(25) xor D(24) xor 
                  D(22) xor D(21) xor D(20) xor D(12) xor D(8) xor D(6) xor 
                  D(5) xor D(2) xor C(2) xor C(5) xor C(6) xor C(8) xor 
                  C(12) xor C(20) xor C(21) xor C(22) xor C(24) xor C(25) xor 
                  C(26) xor C(27) xor C(28) xor C(30);
  
    NewCRC(29) := D(31) xor D(29) xor D(28) xor D(27) xor D(26) xor D(25) xor 
                  D(23) xor D(22) xor D(21) xor D(13) xor D(9) xor D(7) xor 
                  D(6) xor D(3) xor C(3) xor C(6) xor C(7) xor C(9) xor 
                  C(13) xor C(21) xor C(22) xor C(23) xor C(25) xor C(26) xor 
                  C(27) xor C(28) xor C(29) xor C(31);
  
    NewCRC(30) := D(30) xor D(29) xor D(28) xor D(27) xor D(26) xor D(24) xor 
                  D(23) xor D(22) xor D(14) xor D(10) xor D(8) xor D(7) xor 
                  D(4) xor C(4) xor C(7) xor C(8) xor C(10) xor C(14) xor 
                  C(22) xor C(23) xor C(24) xor C(26) xor C(27) xor C(28) xor 
                  C(29) xor C(30);
  
    NewCRC(31) := D(31) xor D(30) xor D(29) xor D(28) xor D(27) xor D(25) xor 
                  D(24) xor D(23) xor D(15) xor D(11) xor D(9) xor D(8) xor 
                  D(5) xor C(5) xor C(8) xor C(9) xor C(11) xor C(15) xor 
                  C(23) xor C(24) xor C(25) xor C(27) xor C(28) xor C(29) xor 
                  C(30) xor C(31);

	elsif  (state_flag = '1' and flag = '1') then
		if (Comp = D2) then 
			Pass <= '1';
			Fail <= '0';
			crc_bsy <= '0';
			
		else
		
			Pass <= '0';
			Fail <= '1';
			crc_bsy <= '1';
			
		end if;
		state_flag := '0';
		flag <= '0';	
		NewCRC := x"00000000"; 
	
	 end if;
	 
	 C			:= NewCRC;
    CRC_out <= NewCRC;
	 
	 if enable = '0' and NewCRC /= x"00000000" then
		ready <= '1';
		
	 elsif enable = '1' then
		ready <= '0';
		
	 end if;
	 

  end process;

end behavior;