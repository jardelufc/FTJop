--------------------------------------------------------------
-- Arquivo:  ham_decod.vhd                              
-- Data:  	 15/09/2008                                                      
-- Autor: 	 David Viana
--   * hamming code
--   * data width: 8
--   * Hamming bits: 4 
--

-----------------------------------------------------------------
library IEEE;

use IEEE.std_logic_1164.all;
use ieee.math_real.all; -- for UNIFORM, TRUNC
use ieee.numeric_std.all; -- for TO_UNSIGNED


entity  ham_decod is
generic (jpc_width : integer);
port(
-----------IN-------------
		reset 		:in std_logic; 
		tclk 			:in std_logic;
		enable		:in std_logic;
		data 			:in std_logic_vector (12 downto 1);
		addr_in		:in std_logic_vector (jpc_width-1 downto 0);
----------OUT--------------
		Ham_out		:out std_logic_vector (7 downto 0)
);

end ham_decod;

architecture behavior of  ham_decod is

	--signal Ham1 : std_logic_vector (7 downto 0);
	--signal test, testF: std_logic_vector (11 downto 0);
	begin
	
	--addr_read : process(tclk)
	--begin
	--if reset = '1' then
		--Ham1 <= x"00";
	
	--else 
	--	Ham_out <= Ham1;
	--end if;
	
	--end process;
		
	process(addr_in)
	
    variable D: std_logic_vector(11 downto 0);
	 variable H1,H2,H3,H4: std_logic;
	 variable temp : std_logic_vector(3 downto 0);
	 variable position: natural;
    variable NewHam: std_logic_vector(7 downto 0);

	--------------	RANDOM TEST -----------------
	-- Seed values for random generator
	--variable seed1, seed2: positive;
	-- Random real-number value in range 0 to 1.0
	--variable rand: real;
	-- Random integer value in range 0..4095
	--variable int_rand: integer;
	---------------------------------------------

	 begin
	 
	 --UNIFORM(seed1, seed2, rand); -- calcule a random value between 0 and 1.
	-- get a 12-bit random value...
	-- 1. rescale to 0..(nearly)11, find integer part
	 --int_rand := INTEGER(TRUNC(rand*11.0)); 
	     
	 D(11 downto 9)		:= data(12 downto 10);
	 D(8)				:= data(9);
	 D(7)				:= data(4);
	 D(6 downto 4)		:= data(8 downto 6);
	 D(3)				:= data(3);
	 D(2)      			:= data(5);
	 D(1 downto 0)		:= data(2 downto 1);

	 --test <= D;

	 -- change the bit value, inserting an error.

	 --if (D(int_rand) = '1') then
		
	--	D(int_rand) := '0';
		
	--	else
	--		D(int_rand) := '1';
			
	-- end if;

	 --testF <= D;

	 NewHam := x"00";
			
	 if (enable = '1' and reset = '0') then
			
			H1 := D(0) xor D(2) xor D(4) xor D(6) xor D(8) xor D(10);
			
			H2 := D(1) xor D(2) xor D(5) xor D(6) xor D(9) xor D(10);			
			
			H3 := D(3) xor D(4) xor D(5) xor D(6) xor D(11);	
			
			H4 := D(7) xor D(8) xor D(9) xor D(10) xor D(11);

			if ((H1 /= '0' or H2 /= '0' or H3 /= '0' or H4 /= '0') and ( H1 /= 'Z' and H2 /= 'Z' and H3 /= 'Z' and H4 /= 'Z') and ( H1 /= 'X' and H2 /= 'X' and H3 /= 'X' and H4 /= 'X') and ( H1 /= 'U' and H2 /= 'U' and H3 /= 'U' and H4 /= 'U'))  then
				
				temp(3) := H4;
				temp(2) := H3;
				temp(1) := H2;
				temp(0) := H1;
				
			   position := to_integer(unsigned(temp(3 downto 0)));--(8*H4) + (4*H3) + (2*H2) + (H1);
				
				if (D(position-1) = '1') then
					D(position-1) := '0';
				
				else
					D(position-1) := '1';
				
				end if;
																				
			end if;

			NewHam(7 downto 4) := D(11 downto 8);
			NewHam(3 downto 1) := D(6 downto 4);
			NewHam(0)  		 := D(2);			
			
			Ham_out <= NewHam;
					
	 end if;

  end process;


--Ham_out <= Ham1;

  
end behavior;