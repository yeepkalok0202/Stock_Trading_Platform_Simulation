import type { NextApiRequest, NextApiResponse } from "next";
import { chunk } from "lodash"; // Import the chunk function from lodash
import chromium from "chrome-aws-lambda";

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  try {
    const results = [];
    const headers = [
      "NO",
      "NAME",
      "CODE",
      "REM",
      "LAST DONE",
      "LACP",
      "CHG",
      "%CHG",
      "VOL ('00)",
      "BUY VOL ('00)",
      "BUY",
      "SELL",
      "SELL VOL",
      "HIGH",
      "LOW",
      "CODE 2",
    ];

    const browser = await chromium.puppeteer.launch({
      args: [...chromium.args, "--hide-scrollbars", "--disable-web-security"],
      defaultViewport: chromium.defaultViewport,
      executablePath: await chromium.executablePath,
      headless: true,
      ignoreHTTPSErrors: true,
    });

    const promises = Array.from({ length: 15 }, async (_, index) => {
      const page = await browser.newPage();
      await page.goto(
        `https://www.bursamalaysia.com/market_information/equities_prices?page=${index + 1}&sort_by=last_done_price&sort_dir=desc`,
        {
          waitUntil: "networkidle0",
          timeout: 50000,
        }
      );

      const data = await page.evaluate(() => {
        const tds = Array.from(document.querySelectorAll("table tr td"));
        return tds.map((td) => td.textContent?.trim());
      });

      const result = chunk(data, 16)?.map((d: any[]) => {
        return d.reduce((p, v, i) => {
          return {
            ...p,
            [headers[i]]: v,
          };
        }, {});
      });

      await page.close();
      return result;
    });

    const pageResults = await Promise.all(promises);
    results.push(...pageResults.flat());

    await browser.close();

    return res.status(200).json({ result: results });
  } catch (error) {
    console.log(error);
    return res.status(400).json({ error });
  }
}
