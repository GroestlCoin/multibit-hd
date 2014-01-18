package org.multibit.hd.ui.views.components.display_amount;

import net.miginfocom.swing.MigLayout;
import org.multibit.hd.core.api.MessageKey;
import org.multibit.hd.core.config.BitcoinConfiguration;
import org.multibit.hd.core.config.Configurations;
import org.multibit.hd.core.config.I18NConfiguration;
import org.multibit.hd.ui.i18n.BitcoinSymbol;
import org.multibit.hd.ui.i18n.Formats;
import org.multibit.hd.ui.i18n.Languages;
import org.multibit.hd.ui.views.AbstractView;
import org.multibit.hd.ui.views.components.Labels;
import org.multibit.hd.ui.views.components.Panels;
import org.multibit.hd.ui.views.fonts.AwesomeDecorator;
import org.multibit.hd.ui.views.fonts.AwesomeIcon;

import javax.swing.*;

/**
 * <p>View to provide the following to UI:</p>
 * <ul>
 * <li>Presentation of a seed phrase display</li>
 * <li>Support for refresh and reveal operations</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class DisplayAmountView extends AbstractView<DisplayAmountModel> {

  // View components
  private JLabel primaryBalanceLabel;
  private JLabel secondaryBalanceLabel;
  private JLabel trailingSymbolLabel;
  private JLabel exchangeLabel;

  /**
   * @param model The model backing this view
   */
  public DisplayAmountView(DisplayAmountModel model) {
    super(model);
  }

  @Override
  public void updateModel() {

  }

  @Override
  public JPanel newPanel() {

    // Create the balance panel - forcing a LTR layout to ensure correct placement of labels
    panel = Panels.newPanel(new MigLayout(
      "fill,ltr,insets 0", // Layout
      "[][][][][]", // Columns
      "[]10[shrink]" // Rows
    ));

    // Create the balance labels (normal size)
    JLabel[] balanceLabels = Labels.newBalanceLabels(false);
    primaryBalanceLabel = balanceLabels[0];
    secondaryBalanceLabel = balanceLabels[1];
    trailingSymbolLabel = balanceLabels[2];
    exchangeLabel = balanceLabels[3];

    // Determine how to add them back into the panel
    if (Languages.isLeftToRight()) {
      panel.add(primaryBalanceLabel, "left,shrink,baseline");
      panel.add(secondaryBalanceLabel, "left,shrink,gap 0");
      panel.add(trailingSymbolLabel, "left,shrink,gap 0");
      panel.add(exchangeLabel, "left,shrink,gap 0");
      panel.add(new JLabel(), "push,wrap"); // Provides a flexible column
    } else {

      panel.add(exchangeLabel, "right,shrink,gap 0");
      panel.add(primaryBalanceLabel, "right,shrink,baseline");
      panel.add(secondaryBalanceLabel, "right,shrink,gap 0");
      panel.add(trailingSymbolLabel, "right,shrink,gap 0");
      panel.add(new JLabel(), "push,wrap"); // Provides a flexible column
    }

    return panel;

  }

  /**
   * Updates the view to reflect the current Bitcoin and local amounts
   */
  public void updateView() {

    BitcoinConfiguration bitcoinConfiguration = Configurations.currentConfiguration.getBitcoinConfiguration();
    I18NConfiguration i18nConfiguration = Configurations.currentConfiguration.getI18NConfiguration();

    String[] bitcoinDisplay = Formats.formatBitcoinBalance(getModel().get().getBitcoinAmount());
    String localDisplay = Formats.formatLocalBalance(getModel().get().getLocalAmount());

    BitcoinSymbol symbol = BitcoinSymbol.valueOf(bitcoinConfiguration.getBitcoinSymbol());

    if (i18nConfiguration.isCurrencySymbolLeading()) {
      handleLeadingSymbol(bitcoinDisplay, symbol);
    } else {
      handleTrailingSymbol(symbol);
    }

    primaryBalanceLabel.setText(bitcoinDisplay[0]);
    secondaryBalanceLabel.setText(bitcoinDisplay[1]);

    exchangeLabel.setText(
      Languages.safeText(
        MessageKey.EXCHANGE_FIAT_RATE,
        "~ $",
        localDisplay
      ));

  }


  /**
   * <p>Place currency symbol before the number</p>
   *
   * @param symbol The symbol to use
   */
  private void handleLeadingSymbol(String[] balance, BitcoinSymbol symbol) {

    // Placement is primary, secondary, trailing, exchange (reading LTR)

    if (BitcoinSymbol.ICON.equals(symbol)) {

      // Icon leads primary balance but decorator will automatically swap which is undesired
      if (Languages.isLeftToRight()) {
        AwesomeDecorator.applyIcon(AwesomeIcon.BITCOIN, primaryBalanceLabel, true, (int) Labels.BALANCE_HEADER_NORMAL_FONT_SIZE);
      } else {
        AwesomeDecorator.applyIcon(AwesomeIcon.BITCOIN, primaryBalanceLabel, false, (int) Labels.BALANCE_HEADER_NORMAL_FONT_SIZE);
      }
      AwesomeDecorator.removeIcon(trailingSymbolLabel);
      trailingSymbolLabel.setText("");

    } else {

      // Symbol leads primary balance
      balance[0] = symbol.getSymbol() + " " + balance[0];
      AwesomeDecorator.removeIcon(primaryBalanceLabel);
      AwesomeDecorator.removeIcon(trailingSymbolLabel);

    }

  }

  /**
   * <p>Place currency symbol after the number</p>
   *
   * @param symbol The symbol to use
   */
  private void handleTrailingSymbol(BitcoinSymbol symbol) {

    if (BitcoinSymbol.ICON.equals(symbol)) {

      // Icon trails secondary balance
      AwesomeDecorator.applyIcon(AwesomeIcon.BITCOIN, trailingSymbolLabel, true, (int) Labels.BALANCE_HEADER_NORMAL_FONT_SIZE);
      AwesomeDecorator.removeIcon(primaryBalanceLabel);
      trailingSymbolLabel.setText("");

    } else {

      // Symbol trails secondary balance
      trailingSymbolLabel.setText(symbol.getSymbol());
      AwesomeDecorator.removeIcon(primaryBalanceLabel);
      AwesomeDecorator.removeIcon(trailingSymbolLabel);

    }

  }
}