# FTJop


This is the Source code for FTJOP, the research developed in Master Thesis of Jardel Silveira: http://livros01.livrosgratis.com.br/cp152022.pdf .

Genuine JOP : https://github.com/jop-devel/jop or http://www.jopdesign.com/



Instructions for mounting in ML401 board:

Jop:
Substituir o arquivo mem_sc que estiver lá por mem_sc_normal, e vai no jop_cpu e comenta o sinal crc_bsy se estiver sendo usado

JOP+Ham:

Substituir o arquivo mem_sc que estiver lá por mem_sc_ham(ou coisa parecida), e vai no jop_cpu e comenta o sinal crc_bsy se estiver sendo usado

JOP+HAM+CRC:

Substitui o arquivo mem_sc que estiver lá por mem_sc, e vai no jop_cpu e descomenta o sinal crc_bsy se estiver sendo usado

JopMethodInfoLesc.java is the program for appendind extra CRC check bytes for each method.




