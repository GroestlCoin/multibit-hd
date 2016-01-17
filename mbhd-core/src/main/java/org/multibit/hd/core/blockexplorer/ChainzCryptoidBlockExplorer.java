package org.multibit.hd.core.blockexplorer;

import java.text.MessageFormat;

/**
 *  <p>blockchain.info BlockExplorer<br>
 *  </p>
 *  
 */
public class ChainzCryptoidBlockExplorer implements BlockExplorer {
  public static final String ID = "blockchain";

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public String getName() {
    return "chainz.cryptoid.info/grs/";
  }

  @Override
  public MessageFormat getTransactionLookupMessageFormat() {
    return new MessageFormat("https://chainz.cryptoid.info/grs/tx.dws?{0}.htm");
  }
}
