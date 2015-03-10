package hr.adriacomsoftware.app.client.smartgwt.kpi.backup;

/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

import com.smartgwt.client.widgets.tree.TreeNode;

public class ChartData extends TreeNode {

    public ChartData(String id, String parentId, String title) {
        setID(id);
        if (parentId != null) {
            setParentID(parentId);
        }
        setTitle(title);
    }

    public static ChartData[] getData() {
        return new ChartData[] {
        	new ChartData("root", null, "Pokazatelji"),
            new ChartData("Financijski", "root", "Financijski"),
            new ChartData("Klijenti", "root", "Klijenti"),
            new ChartData("Procesi", "root", "Procesi"),
            new ChartData("Djelatnici", "root", "Djelatnici"),
            new ChartData("2002", "Financijski", "2002"),
            new ChartData("2003", "Klijenti", "2002"),
            new ChartData("2004", "Klijenti", "2004"),
            new ChartData("Q1-2002", "2002", "Q1-2002"),
            new ChartData("Q2-2002", "2002", "Q2-2002"),
            new ChartData("Q3-2002", "2002", "Q3-2002"),
            new ChartData("Q4-2002", "2002", "Q4-2002"),
            new ChartData("Q1-2003", "2003", "Q1-2003"),
            new ChartData("Q2-2003", "2003", "Q2-2003"),
            new ChartData("Q3-2003", "2003", "Q3-2003"),
            new ChartData("Q4-2003", "2003", "Q4-2003"),
            new ChartData("Q1-2004", "2004", "Q1-2004"),
            new ChartData("Q2-2004", "2004", "Q2-2004"),
            new ChartData("Q3-2004", "2004", "Q3-2004"),
            new ChartData("Q4-2004", "2004", "Q4-2004"),
            new ChartData("1/1/2002", "Q1-2002", "1/1/2002"),
            new ChartData("2/1/2002", "Q1-2002", "2/1/2002"),
            new ChartData("3/1/2002", "Q1-2002", "3/1/2002"),
            new ChartData("4/1/2002", "Q2-2002", "4/1/2002"),
            new ChartData("5/1/2002", "Q2-2002", "5/1/2002"),
            new ChartData("6/1/2002", "Q2-2002", "6/1/2002"),
            new ChartData("7/1/2002", "Q3-2002", "7/1/2002"),
            new ChartData("8/1/2002", "Q3-2002", "8/1/2002"),
            new ChartData("9/1/2002", "Q3-2002", "9/1/2002"),
            new ChartData("10/1/2002", "Q4-2002", "10/1/2002"),
            new ChartData("11/1/2002", "Q4-2002", "11/1/2002"),
            new ChartData("12/1/2002", "Q4-2002", "12/1/2002"),
            new ChartData("1/1/2003", "Q1-2003", "1/1/2003"),
            new ChartData("2/1/2003", "Q1-2003", "2/1/2003"),
            new ChartData("3/1/2003", "Q1-2003", "3/1/2003"),
            new ChartData("4/1/2003", "Q2-2003", "4/1/2003"),
            new ChartData("5/1/2003", "Q2-2003", "5/1/2003"),
            new ChartData("6/1/2003", "Q2-2003", "6/1/2003"),
            new ChartData("7/1/2003", "Q3-2003", "7/1/2003"),
            new ChartData("8/1/2003", "Q3-2003", "8/1/2003"),
            new ChartData("9/1/2003", "Q3-2003", "9/1/2003"),
            new ChartData("10/1/2003", "Q4-2003", "10/1/2003"),
            new ChartData("11/1/2003", "Q4-2003", "11/1/2003"),
            new ChartData("12/1/2003", "Q4-2003", "12/1/2003"),
            new ChartData("1/1/2004", "Q1-2004", "1/1/2004"),
            new ChartData("2/1/2004", "Q1-2004", "2/1/2004"),
            new ChartData("3/1/2004", "Q1-2004", "3/1/2004"),
            new ChartData("4/1/2004", "Q2-2004", "4/1/2004"),
            new ChartData("5/1/2004", "Q2-2004", "5/1/2004"),
            new ChartData("6/1/2004", "Q2-2004", "6/1/2004"),
            new ChartData("7/1/2004", "Q3-2004", "7/1/2004"),
            new ChartData("8/1/2004", "Q3-2004", "8/1/2004"),
            new ChartData("9/1/2004", "Q3-2004", "9/1/2004"),
            new ChartData("10/1/2004", "Q4-2004", "10/1/2004"),
            new ChartData("11/1/2004", "Q4-2004", "11/1/2004"),
            new ChartData("12/1/2004", "Q4-2004", "12/1/2004"),
        };
    }

}