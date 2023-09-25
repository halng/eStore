module.exports = {
    collectCoverage: true,
    collectCoverageFrom: ['src/**/*.tsx'],
    coverageThreshold: {
        global: {
            lines: 20,
        },
    },
    coverageDirectory: 'coverage',
    testEnvironment: 'jsdom',
    moduleNameMapper: {
        '\\.(css|less|sass|scss)$': '<rootDir>/__mocks__/styleMock.js',
        '\\.(gif|ttf|eot|svg)$': '<rootDir>/__mocks__/fileMock.js',
    },
}
