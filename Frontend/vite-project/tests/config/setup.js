// import { expect, afterEach } from "vitest";
import { afterEach } from "vitest";
import { cleanup } from "@testing-library/react";
// import matchers from "@testing-library/jest-dom/matchers";
import "@testing-library/jest-dom/vitest";

// expect.extend(matchers);

afterEach(() => {
    cleanup();
});