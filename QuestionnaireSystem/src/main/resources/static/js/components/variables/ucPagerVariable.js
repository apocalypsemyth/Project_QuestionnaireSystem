/**
 * ucPager Component全域變數
 */
class UcPagerProperty {
	static #intPageIndex;
	static #executeFuncWithUcPager;
	static #getIntPageIndex() {
		return this.#intPageIndex;
	}
	static #setIntPageIndex(value) {
		this.#intPageIndex = value;
	}
	static #getExecuteFuncWithUcPager() {
		return this.#executeFuncWithUcPager;
	}
	static #setExecuteFuncWithUcPager(value) {
		this.#executeFuncWithUcPager = value;
	}

	static get INT_PAGE_INDEX() {
		return this.#getIntPageIndex();
	}
	static set INT_PAGE_INDEX(value) {
		if (typeof value !== "number") throw new Error(EnumError.CANNOT_SET);
		this.#setIntPageIndex(value);
	}
	static get EXECUTE_FUNC_WITH_UCPAGER() {
		return this.#getExecuteFuncWithUcPager();
	}
	static set EXECUTE_FUNC_WITH_UCPAGER(value) {
		if (typeof value !== "function") throw new Error(EnumError.CANNOT_SET);
		this.#setExecuteFuncWithUcPager(value);
	}
	static get ARR_QUERY_PARAM_KEY() {
		return new UcPagerProperty(["keyword", "startDate", "endDate", "index"]);
	}
	static set ARR_QUERY_PARAM_KEY(value) {
		throw new Error(EnumError.CANNOT_SET);
	}

	constructor(key) {
		Object.defineProperties(this, {
			key: {
				enumerable: true,
				set: (value) => {
					throw new Error(EnumError.CANNOT_SET);
				},
				get: () => key,
			},
		});
	}
}

// ucPager Component控制項
const divUcPagerContainer = "#divUcPagerContainer";
const aLinkUcPager = "a[id*=aLinkUcPager]";
