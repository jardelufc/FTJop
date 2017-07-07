/*
 * Copyright (c) 2007,2008, Stefan Hepp
 *
 * This file is part of JOPtimizer.
 *
 * JOPtimizer is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * JOPtimizer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jopdesign.libgraph.cfg.block;

import com.jopdesign.libgraph.cfg.statements.quad.QuadStatement;

/**
 * @author Stefan Hepp, e0026640@student.tuwien.ac.at
 */
public class QuadCode extends CodeBlock {

    public QuadCode(BasicBlock block) {
        super(block);
    }

    public void addStatement(QuadStatement quadStmt) {
        addStatement(size(), quadStmt);
    }

    public void insertStatement(int pos, QuadStatement quadStmt) {
        addStatement(pos, quadStmt);
    }

    public QuadStatement deleteQuadStatement(int pos) {
        return (QuadStatement) deleteStatement(pos);
    }

    public QuadStatement getQuadStatement(int pos) {
        return (QuadStatement) getStatement(pos);
    }

    public void setQuadStatement(int i, QuadStatement stmt) {
        setStatement(i, stmt);
    }
}
