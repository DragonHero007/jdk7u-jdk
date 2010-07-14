/*
 * Copyright 2010 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
/*
 * @test
 * @bug 6882687
 * @summary KerberosTime too imprecise
 */

import sun.security.krb5.internal.KerberosTime;

public class MicroTime {
    public static void main(String[] args) throws Exception {
        // We count how many different KerberosTime values
        // can be acquired within one second.
        KerberosTime t1 = new KerberosTime(true);
        KerberosTime last = t1;
        int count = 0;
        while (true) {
            KerberosTime t2 = new KerberosTime(true);
            if (t2.getTime() - t1.getTime() > 1000) break;
            if (!last.equals(t2)) {
                last = t2;
                count++;
            }
        }
        // We believe a nice KerberosTime can at least tell the
        // difference of 100 musec.
        if (count < 10000) {
            throw new Exception("What? only " + (1000000/count) +
                    " musec precision?");
        }
    }
}